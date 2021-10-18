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

import com.forum.dto.PostDTO;
import com.forum.dto.PostResponse;
import com.forum.entity.Post;
import com.forum.service.PostService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class PostController {

	private PostService postService;

	@Autowired
	public PostController(PostService postService) {
		this.postService = postService;
	}

	@GetMapping("/get-all-by-like")
	public List<PostResponse> getAllByLike(){
		return this.postService.getTop10ByLikeCount();
	}
	
	@GetMapping("/get-last-24-hours")
	public List<PostResponse> getLast24Hours(@PathParam("p")int p ){
		return this.postService.last24Hours(p);
	}
	
	@GetMapping("/get-post")
	public PostResponse getPost(@PathParam("id")Long id) {
		return this.postService.getPost(id);
	}
	
	@GetMapping("/last-posts")
	public List<PostResponse> lastPosts(){
		return this.postService.lastPosts();
	}
	
	@DeleteMapping("delete-post")
	public String deletePostById(@PathParam("post_id")Long post_id) {
		return this.postService.deletePostById(post_id);
	}
	
	@GetMapping("/get-all")
	public List<PostResponse> getAll(){
		return this.postService.findAll();
	}
	
	@GetMapping("/get-all-by-category-last-24")
	public List<PostResponse> getByCategoryIdAndLast24Hours(@PathParam("category_id,p")Long category_id,int p){
		return this.postService.getAllByCategoryIdAndLast24Hours(category_id,p);
	}
	
	@GetMapping("/get-all-by-category")
	public List<PostResponse> getByCategoryId(@PathParam("category_id,p")Long category_id,int p){
		return this.postService.getAllByCategoryId(category_id,p);
	}
	
	@PostMapping("/create-post")
	public Post createPost(@RequestBody PostDTO post) {
		
		return this.postService.createPost(post);
	}
	
}
