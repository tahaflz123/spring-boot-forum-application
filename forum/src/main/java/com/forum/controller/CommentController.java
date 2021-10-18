package com.forum.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forum.dto.CommentDTO;
import com.forum.dto.CommentResponse;
import com.forum.service.CommentService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class CommentController {

	private CommentService commentService;

	@Autowired
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	@GetMapping("/get-post-comments")
	public List<CommentResponse> getAllCommentsForPost(@PathParam("p,id")int p, Long id) {
		return this.commentService.getAllCommentsForPost(p,id);
	}
	
	@PostMapping("/delete-comment")
	public String deleteCommentById(@PathParam("comment_id")Long comment_id) {
		return this.commentService.deletePostById(comment_id);
	}
	
	@PostMapping("/add-comment")
	public String addComment(@PathParam("id") Long id,@RequestBody CommentDTO comment) {
		return this.commentService.addComment(id,comment);
	}
	
}
