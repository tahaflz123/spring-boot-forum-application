package com.forum.service;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.forum.config.TokenFilter;
import com.forum.entity.Comment;
import com.forum.entity.Post;
import com.forum.entity.User;
import com.forum.entity.Vote;
import com.forum.exception.AuthException;
import com.forum.exception.VoteException;
import com.forum.repository.CommentRepository;
import com.forum.repository.PostRepository;
import com.forum.repository.UserRepository;
import com.forum.repository.VoteRepository;

@Service
public class VoteService {

	private VoteRepository voteRepository;
	private PostRepository postRepository;
	private UserRepository userRepository;
	private CommentRepository commentRepository;
	private AuthService authService;
	
	@Autowired
	public VoteService(VoteRepository voteRepository, PostRepository postRepository,
			CommentRepository commentRepository,AuthService authService,
			UserRepository userRepository) {
		this.voteRepository = voteRepository;
		this.postRepository = postRepository;
		this.commentRepository = commentRepository;
		this.authService = authService;
		this.userRepository = userRepository;
	}

	public String votePost(Long id) {
		Post post = this.postRepository.findById(id).get();
		User user = this.authService.getUser();
		if(user != null) {
			Vote vote = this.voteRepository.findByUserIdAndPostId(user.getUserId(),id);
			if(post != null) {
				if(vote != null) {
					post.setZPoint(post.getZPoint() - 1);
					this.voteRepository.deleteById(vote.getId());
					return post.getZPoint().toString();
				}else if(vote == null) {
					post.setZPoint(post.getZPoint() + 1);
					this.voteRepository.save(this.mapToPostVote(post, user));
					return post.getZPoint().toString();
				}
			}
		}else {
			throw new VoteException("Beğenilemedi...");
		}
		throw new AuthException();
	}
	
	public String voteComment(Long id) {
		Comment comment = this.commentRepository.findById(id).get();
		User user = this.authService.getUser();
		if(user != null) {
			Vote vote = this.voteRepository.findByUserIdAndCommentId(user.getUserId(),id);
			if(comment != null) {
				if(vote != null) {
					comment.setLikeCount(comment.getLikeCount() - 1);
					this.voteRepository.delete(vote);
					return comment.getLikeCount().toString();
				}else if(vote == null) {
					comment.setLikeCount(comment.getLikeCount() + 1);
					this.voteRepository.save(this.mapToCommentVote(comment, user));
					return comment.getLikeCount().toString();
				}
			}
			throw new VoteException("Beğenebilmek için giriş yapmalısınız...");
		}
		throw new AuthException();
	}
	
	public Vote mapToPostVote(Post post,User user) {
		return Vote.builder().postId(post.getId()).userId(user.getUserId()).commentId(null).build();
	}
	public Vote mapToCommentVote(Comment comment,User user) {
		return Vote.builder().postId(null).userId(user.getUserId()).commentId(comment.getId()).build();
	}

}
