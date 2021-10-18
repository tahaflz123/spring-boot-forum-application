package com.forum.controller;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forum.service.ReportService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class ReportController {
	
	@Autowired
	private ReportService reportService;
	
	
	@PostMapping("/report/post")
	public String reportPost(@PathParam("post_id")Long post_id) {
		return this.reportService.reportPost(post_id);
	}
	
	@PostMapping("/report/comment")
	public String reportComment(@PathParam("comment_id")Long comment_id) {
		return this.reportService.reportComment(comment_id);
	}
	
	@PostMapping("/report/user")
	public String reportUser(@PathParam("user_id")Long user_id) {
		return this.reportService.reportUser(user_id);
	}
	

}
