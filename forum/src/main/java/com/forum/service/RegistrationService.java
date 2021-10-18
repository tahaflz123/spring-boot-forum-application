package com.forum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forum.dto.RegistrationDTO;
import com.forum.entity.User;
import com.forum.entity.UserRole;
import com.forum.repository.UserRepository;

@Service
public class RegistrationService {


	private UserService appUserService;
	

	@Autowired 
	public RegistrationService(UserService appUserService, UserRepository appUserRepository) {
		this.appUserService = appUserService;
	}


	public String register(RegistrationDTO request) {
			return appUserService.signUpUser( new User	(
	        
					request.getEmail(),
					request.getPassword(),
					request.getUsername(),
					UserRole.USER));


	}
}
