package com.forum.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forum.entity.Report;
import com.forum.entity.User;
import com.forum.exception.AuthException;
import com.forum.exception.ReportException;
import com.forum.repository.CommentRepository;
import com.forum.repository.PostRepository;
import com.forum.repository.ReportRepository;

@Service
public class ReportService {

	private ReportRepository reportRepository;
	private AuthService authService;
	private PostRepository postRepository;
	private CommentRepository commentRepository;
	
	@Autowired
	public ReportService(ReportRepository reportRepository, AuthService authService,CommentRepository commentRepository
			,PostRepository postRepository) {
		this.reportRepository = reportRepository;
		this.authService = authService;
		this.postRepository = postRepository;
		this.commentRepository = commentRepository;
	}

	public String reportPost(Long postId) {
		User user = this.authService.getUser();
		boolean post = this.postRepository.existsById(postId);
		boolean reported = this.reportRepository.existsByReporterUserIdAndPostId(user.getUserId(),postId);
		if(user != null) {
			if(post == false && reported == false) {
				this.reportRepository.save(this.mapToReport(user.getUserId(),postId, null, null));
				return "Reported";
			}
		}
		throw new AuthException();
		
	}
	
	public String reportUser(Long userId) {
		User user = this.authService.getUser();
		boolean reported = this.reportRepository.existsByReporterUserIdAndUserId(user.getUserId(),userId);
		
		if(user != null) {
			if(reported == false) {
				this.reportRepository.save(this.mapToReport(user.getUserId(),null, userId, null));
				return "Reported";
			}
		}
		throw new AuthException();
		
	}
	
	public String reportComment(Long commentId) {
		User user = this.authService.getUser();
		boolean comment = this.commentRepository.existsById(commentId);
		boolean reported = this.reportRepository.existsByReporterUserIdAndCommentId(user.getUserId(),commentId);
		
		if(user != null) {
			if(comment == false && reported == false) {
				this.reportRepository.save(this.mapToReport(user.getUserId(),null, null, commentId));
				return "Reported";
			}
		}
		throw new AuthException();
	}
	
	
	
	
	
	public Report mapToReport(Long reporterUserId,Long postId,Long userId,Long commentId) {
		return Report.builder().reporterUserId(reporterUserId).commentId(commentId).postId(postId).userId(userId).date(new Date()).build();
	}
	
	
}
