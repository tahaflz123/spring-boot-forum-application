package com.forum.controller;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forum.service.VoteService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class VoteController {

	@Autowired
	private VoteService voteService;
	
	@PostMapping("/vote-post")
	public String votePost(@PathParam("post_id") Long post_id) {
		return voteService.votePost(post_id);
	}
	
	@PostMapping("/vote-comment")
	public String voteComment(@PathParam("comment_id") Long comment_id) {
		return voteService.voteComment(comment_id);
	}
	
}
