package com.forum.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.forum.dto.PostDTO;
import com.forum.dto.PostResponse;
import com.forum.dto.PostResults;
import com.forum.entity.Post;
import com.forum.entity.User;
import com.forum.entity.Vote;
import com.forum.exception.AuthException;
import com.forum.exception.PostException;
import com.forum.repository.PostRepository;
import com.forum.repository.UserRepository;
import com.forum.repository.VoteRepository;

@Service
public class PostService {


	private PostRepository postRepository;
	private VoteRepository voteRepository;
    private AuthService authService;
    private UserRepository userRepository;

    @Autowired
	public PostService(PostRepository postRepository,UserRepository userRepository, VoteRepository voteRepository,AuthService authService) {
		this.postRepository = postRepository;
		this.voteRepository = voteRepository;
		this.authService = authService;
		this.userRepository = userRepository;
	}


	public Post createPost(PostDTO post) {
		User user = this.authService.getUser();
		if(user != null)
		{
			user.setPostCount(user.getPostCount());
			this.userRepository.save(user);

			this.postRepository.save(this.mapToPost(post,user));
			return null;
		}
		throw new AuthException();
	}

	
	public Post mapToPost(PostDTO post,User user) {
		return Post.builder().user(user).text(post.getText()).header(post.getHeader()).categoryId(post.getCategoryId()).postDate(new Date()).build();
	}
	
	public List<PostResponse> getTop10ByLikeCount(){

		Pageable pageable = PageRequest.of(0, 10,Sort.by(Order.desc("ZPoint")));
		Page<Post> posts = this.postRepository.findAll(pageable);
		List<PostResponse> response = new ArrayList<PostResponse>();
		for(Post post : posts) {
			PostResponse postResponse = new PostResponse();
			postResponse.setHeader(post.getHeader());
			postResponse.setLikeCount(post.getZPoint());
			postResponse.setPostId(post.getId());
			postResponse.setText(post.getText());
			postResponse.setUserId(post.getUser().getUserId());
			postResponse.setUsername(post.getUser().getUsername());
			User user = this.authService.getUser();
			if(user != null) {

				Vote vote = this.voteRepository.findByUserIdAndPostId(user.getUserId(), post.getId());
				if(vote != null){
					postResponse.setLiked(true);
				
				}
			}
			response.add(postResponse);
		}
		return response;
		
	}


	public PostResponse getPost(Long id) {
		Post post = this.postRepository.findByIdAndDeleted(id,false);
		if(post != null) {
				PostResponse response = new PostResponse();
				response.setHeader(post.getHeader());
				response.setPostId(post.getId());
				response.setUserId(post.getUser().getUserId());
				response.setText(post.getText());
				response.setPostDate(post.getPostDate());
				response.setUsername(post.getUser().getUsername());
				response.setLikeCount(post.getZPoint());
				User user = this.authService.getUser();
				if(user != null) {

					Vote vote = this.voteRepository.findByUserIdAndPostId(user.getUserId(), post.getId());
					if(vote != null){
						response.setLiked(true);
					
					}
				}
				return response;
		}
		throw new PostException("No post...");
	}
	
	public List<PostResponse> getAllByCategoryIdAndLast24Hours(Long categoryId,int p){
		Pageable pageable = PageRequest.of(p,30,Sort.by(Order.desc("ZPoint")));
		Date date = new Date().from(Instant.now().minusSeconds(24 * 60 * 60));
		Page<Post> posts = this.postRepository.findAllByDeletedFalseAndCategoryIdAndPostDateAfter(categoryId,date,pageable);
		List<PostResponse> response = new ArrayList<PostResponse>();
		for(Post post : posts) {
			PostResponse postResponse = new PostResponse();
			postResponse.setHeader(post.getHeader());
			postResponse.setLikeCount(post.getZPoint());
			postResponse.setPostId(post.getId());
			postResponse.setCurrentPage(pageable.getPageNumber());
			postResponse.setPageSize(posts.getTotalPages());
			postResponse.setText(post.getText());
			postResponse.setPostDate(post.getPostDate());
			postResponse.setUserId(post.getUser().getUserId());
			postResponse.setUsername(post.getUser().getUsername());
			User user = this.authService.getUser();
			if(user != null) {

				Vote vote = this.voteRepository.findByUserIdAndPostId(user.getUserId(), post.getId());
				if(vote != null){
					postResponse.setLiked(true);
				
				}
			}
			response.add(postResponse);
		}
		return response;
	}
	
	public List<PostResponse> getAllByCategoryId(Long categoryId,int p){
		Pageable pageable = PageRequest.of(p,30,Sort.by(Order.desc("postDate")));
		Page<Post> posts = this.postRepository.findAllByDeletedFalseAndCategoryId(categoryId,pageable);
		List<PostResponse> response = new ArrayList<PostResponse>();
		for(Post post : posts) {
			PostResponse postResponse = new PostResponse();
			postResponse.setHeader(post.getHeader());
			postResponse.setLikeCount(post.getZPoint());
			postResponse.setPostId(post.getId());
			postResponse.setText(post.getText());
			postResponse.setPostDate(post.getPostDate());
			postResponse.setCurrentPage(pageable.getPageNumber());
			postResponse.setPageSize(posts.getTotalPages());
			postResponse.setUserId(post.getUser().getUserId());
			postResponse.setUsername(post.getUser().getUsername());
			User user = this.authService.getUser();
			if(user != null) {

				Vote vote = this.voteRepository.findByUserIdAndPostId(user.getUserId(), post.getId());
				if(vote != null){
					postResponse.setLiked(true);
				
				}
			}
			response.add(postResponse);
		}
		return response;
	}


	public String deletePostById(Long id) {
		Post post = this.postRepository.findById(id).get();
		post.setDeleted(true);
		this.postRepository.save(post);
		return "Silindi...";
	}
	
	public List<PostResponse> last24Hours(int pageNumber){
		Pageable pageable = PageRequest.of(pageNumber, 30);
		System.out.println(pageable.getOffset());
		
		Date date = new Date();
		Page<Post> posts = this.postRepository.findByDeletedFalseAndPostDateAfter(date.from(Instant.now().minusSeconds((24 * 60) * 60)),pageable);
		System.err.println(posts.getTotalPages());
		List<PostResponse> response = new ArrayList<PostResponse>();
		for(Post post : posts) {
			PostResponse postResponse = new PostResponse();
			System.err.println(pageable.getPageSize());
			System.err.println(posts.getSize());
			System.err.println(posts.getTotalElements());
			postResponse.setHeader(post.getHeader());
			postResponse.setLikeCount(post.getZPoint());
			postResponse.setPostId(post.getId());
			postResponse.setText(post.getText());
			postResponse.setPostDate(post.getPostDate());
			postResponse.setCurrentPage(pageable.getPageNumber());
			postResponse.setPageSize(posts.getTotalPages());
			postResponse.setUserId(post.getUser().getUserId());
			postResponse.setUsername(post.getUser().getUsername());
			User user = this.authService.getUser();
			if(user != null) {

				Vote vote = this.voteRepository.findByUserIdAndPostId(user.getUserId(), post.getId());
				if(vote != null){
					postResponse.setLiked(true);
				
				}
			}
			response.add(postResponse);
		}
		return response;
	}
	
	public List<PostResponse> findUserPosts(int page,String username) {
		User dbUser = this.userRepository.findByUsername(username);
		Pageable pageable = PageRequest.of(page, 10, Sort.by(Order.by("postDate")));
		Page<Post> posts = this.postRepository.findAllByDeletedFalseAndUser(dbUser,pageable);
		List<PostResponse> response = new ArrayList<PostResponse>();
		for(Post post : posts) {
			PostResponse postResponse = new PostResponse();
			postResponse.setHeader(post.getHeader());
			postResponse.setLikeCount(post.getZPoint());
			postResponse.setPostId(post.getId());
			postResponse.setText(post.getText());
			postResponse.setCurrentPage(pageable.getPageNumber());
			postResponse.setPageSize(posts.getTotalPages());
			postResponse.setPostDate(post.getPostDate());
			postResponse.setUserId(post.getUser().getUserId());
			postResponse.setUsername(post.getUser().getUsername());
			User user = this.authService.getUser();
			if(user != null) {

				Vote vote = this.voteRepository.findByUserIdAndPostId(user.getUserId(), post.getId());
				if(vote != null){
					postResponse.setLiked(true);
				
				}
			}
			response.add(postResponse);
		}
		return response;
	}
	
	public List<PostResponse> findAll() {
		List<Post> posts = this.postRepository.findAllByDeleted(false);
		List<PostResponse> response = new ArrayList<PostResponse>();
		for(Post post : posts) {
			PostResponse postResponse = new PostResponse();
			postResponse.setHeader(post.getHeader());
			postResponse.setLikeCount(post.getZPoint());
			postResponse.setPostId(post.getId());
			postResponse.setText(post.getText());
			postResponse.setUserId(post.getUser().getUserId());
			postResponse.setUsername(post.getUser().getUsername());
			User user = this.authService.getUser();
			if(user != null) {

				Vote vote = this.voteRepository.findByUserIdAndPostId(user.getUserId(), post.getId());
				if(vote != null){
					postResponse.setLiked(true);
				
				}
			}
			response.add(postResponse);
		}
		return response;
	}


	public List<PostResults> postSearch(String q) {
		Pageable pageable = PageRequest.of(0, 10);
		List<Post> posts = this.postRepository.findByDeletedFalseAndHeaderLike(q,pageable);
		
		List<PostResults> results = new ArrayList<PostResults>();
		for(Post post : posts) {
			PostResults postResults = new PostResults();
			postResults.setPost_id(post.getId());
			postResults.setHeader(post.getHeader());
			results.add(postResults);
		}
		
		
		return results;
	}


	public List<PostResponse> lastPosts() {
		Pageable pageable = PageRequest.of(0, 10,Sort.by(Order.desc("postDate")));
		Date date = new Date().from(Instant.now().minusSeconds(24 * 60 * 60));
		Page<Post> posts = this.postRepository.findAllByDeletedFalseAndPostDateAfter(date,pageable);		List<PostResponse> response = new ArrayList<PostResponse>();
		for(Post post : posts) {
			PostResponse postResponse = new PostResponse();
			postResponse.setHeader(post.getHeader());
			postResponse.setCurrentPage(pageable.getPageNumber());
			postResponse.setPageSize(pageable.getPageSize());
			postResponse.setLikeCount(post.getZPoint());
			postResponse.setPostId(post.getId());
			postResponse.setText(post.getText());
			postResponse.setPostDate(post.getPostDate());
			postResponse.setUserId(post.getUser().getUserId());
			postResponse.setUsername(post.getUser().getUsername());
			User user = this.authService.getUser();
			if(user != null) {

				Vote vote = this.voteRepository.findByUserIdAndPostId(user.getUserId(), post.getId());
				if(vote != null){
					postResponse.setLiked(true);
				
				}
			}
			response.add(postResponse);
		}
		return response;
	}
}
