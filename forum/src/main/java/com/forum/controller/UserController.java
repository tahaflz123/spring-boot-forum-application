package com.forum.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forum.dto.PostResponse;
import com.forum.dto.SearchDTO;
import com.forum.dto.UserResponse;
import com.forum.entity.Contact;
import com.forum.entity.User;
import com.forum.exception.AuthException;
import com.forum.repository.ContactRepository;
import com.forum.service.AuthService;
import com.forum.service.PostService;
import com.forum.service.UserService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class UserController {

	private UserService userService;
	private AuthService authService;
	private PostService postService;
	private ContactRepository contactRepository;
	
	@Autowired
	public UserController(AuthService authService,
			ContactRepository contactRepository, UserService userService,PostService postService) {
		this.authService = authService;
		this.postService = postService;
		this.contactRepository = contactRepository;
		this.userService = userService;
	}

	//-----------Contact----------------\\
	@PostMapping("/contact")
	public void contact(@RequestBody Contact contactCredentials){
		this.contactRepository.save(contactCredentials);
	}	
	
	@GetMapping("/admin/user-messages")
	public List<Contact> getUserMessages(){
		return this.contactRepository.findAll();
	}
	
	@DeleteMapping("/admin/delete-message")
	public void deleteMessage(@PathParam("id") Long id) {
		this.contactRepository.deleteById(id);
	}
	//------------------------------------\\
	@GetMapping("/my-profile")
	public User appUser(){
		User user = this.authService.getUser();
		if(user != null ) {
			return user;
		}
		throw new AuthException();
	}
	
	@GetMapping("/user/{username}")
	public UserResponse getUserWithStories(@PathVariable("username") String username) {
		return this.userService.getUserByUsername(username);
	}
	
	@GetMapping("/post/{username}")
	public List<PostResponse> userPosts(@PathVariable("username")String username,@PathParam("p")int p){
		return this.postService.findUserPosts(p,username);
	}
	
	//Search
	@PostMapping("/search")
	public SearchDTO search(@PathParam("q")String q) {
		return this.userService.search(q);
	}
	
	@GetMapping("/get-username")
	public String getUserNameWithToken() {
		return this.userService.getUserNameWithToken();
	}
	
	
	
}
