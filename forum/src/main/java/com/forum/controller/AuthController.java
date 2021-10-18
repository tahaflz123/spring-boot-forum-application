package com.forum.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.forum.dto.AuthDTO;
import com.forum.service.AuthService;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class AuthController {

	@Autowired
	private AuthService authService;
	
	
	
	@PostMapping("/api/login")
	public Object handleAuthentication(@RequestBody AuthDTO  credentials,HttpServletRequest request) {
		System.out.println(credentials.getUsername());
		System.err.println(request.getRemoteAddr());
		return this.authService.authenticate(credentials);
	}
	
	
}
