package com.forum.controller;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forum.service.PasswordResetTokenService;

@RestController
@RequestMapping("/api")
public class PasswordResetTokenController {
	
	@Autowired
	private PasswordResetTokenService passwordResetTokenService;
	
	@PostMapping("/forgot-password")
	public String forgotPassword(@PathParam("email")String email) {
		return this.passwordResetTokenService.forgotPassword(email);
	}
	
	@GetMapping("/reset-password")
	public String resetPassword(@PathParam("token,password")String token) {
		return this.passwordResetTokenService.resetPassword(token);
	}
	
	
	@PostMapping("/reset-password")
	public String resetPassword(@PathParam("token,password")String token,String password) {
		return this.passwordResetTokenService.resetPassword(token, password);
	}
	
	

}
