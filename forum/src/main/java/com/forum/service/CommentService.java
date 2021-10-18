package com.forum.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.forum.dto.CommentDTO;
import com.forum.dto.CommentResponse;
import com.forum.entity.Comment;
import com.forum.entity.Post;
import com.forum.entity.User;
import com.forum.entity.Vote;
import com.forum.exception.AuthException;
import com.forum.repository.CommentRepository;
import com.forum.repository.PostRepository;
import com.forum.repository.VoteRepository;

@Service
public class CommentService {

	
    private AuthService authService;
	private CommentRepository commentRepository;
	private PostRepository postRepository;
	private VoteRepository voteRepository;
	
	@Autowired
	public CommentService(AuthService authService,VoteRepository voteRepository,CommentRepository commentRepository,PostRepository postRepository) {
		this.authService = authService;
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.voteRepository = voteRepository;
	}
	
	
	public String addComment(Long id, CommentDTO comment) {
		User user = this.authService.getUser();
		Post post = this.postRepository.findById(id).get();
		if(user != null && post != null) {
			this.commentRepository.save(mapToComment(comment,user,post));
			return null;
		}
		
		throw new AuthException();
	}
	
	public Comment mapToComment(CommentDTO comment,User user,Post post) {
		return Comment.builder().user(user).post(post).text(comment.getText()).commentDate(new Date()).build();
	}


	public List<CommentResponse> getAllCommentsForPost(int p,Long post_id) {
		Pageable pageable = PageRequest.of(p, 50);
		Page<Comment> comments = this.commentRepository.findAllByPostIdAndDeletedFalse(post_id,pageable);
		List<CommentResponse> response = new ArrayList<CommentResponse>();
		
		for(Comment comment : comments) {
			CommentResponse resp = new CommentResponse();
			resp.setPost_id(post_id);
			resp.setId(comment.getId());
			resp.setText(comment.getText());
			resp.setCurrentPage(pageable.getPageNumber());
			resp.setPageSize(comments.getTotalPages());
			resp.setCommentDate(comment.getCommentDate());
			resp.setUsername(comment.getUser().getUsername());
			resp.setUserId(comment.getUser().getUserId());
			resp.setLikeCount(comment.getLikeCount());
			User user = this.authService.getUser();
			if(user != null) {
				Vote vote = this.voteRepository.findByUserIdAndCommentId(post_id, comment.getId());
				if(vote != null) {
					resp.setLiked(true);
				}
			}
			response.add(resp);
		}
		
		return response;
	}


	public String deletePostById(Long comment_id) {
        Optional<Comment> comment = this.commentRepository.findById(comment_id);
		comment.get().setDeleted(true);
		this.commentRepository.save(comment.get());
        return "Yorum silindi";
	}

}
