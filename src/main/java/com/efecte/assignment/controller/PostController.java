package com.efecte.assignment.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.efecte.assignment.model.Post;
import com.efecte.assignment.repository.PostRepository;
import com.efecte.assignment.service.PostService;
import com.efecte.assignment.exception.ResourceNotFoundException;

@CrossOrigin("*")
@RestController
@RequestMapping({"/api"})
public class PostController {

	@Autowired
	private PostService postService;
	
	@GetMapping("/posts")
	public List<Post> getPosts(){
		return postService.getAllPosts();
	}
	
	@PostMapping("/posts")
	public ResponseEntity<Post> createPost(@Valid @RequestBody Post post) {
		if(validatePost(post)) {
			return ResponseEntity.ok(postService.createPost(post));			
		}
		else {
			throw new ResourceNotFoundException("Post message too long. Max length is 200.");
		}
	}
	
	@GetMapping("/post/{postId}")
	public ResponseEntity<Post> getPostById(@PathVariable Long postId){
		Post post = getById(postId);
		return ResponseEntity.ok(post);		
	}
	
	@PutMapping("/post/{postId}")
	public ResponseEntity<Post> updatePost(@PathVariable Long postId, @Valid @RequestBody Post postReq) {
		if(validatePost(postReq)) {
			Post post = getById(postId);
			if(post != null) {
				post.setColor(postReq.getColor());
				post.setMessage(postReq.getMessage());
			}
			return ResponseEntity.ok(postService.updatePost(post));			
		}else {
			throw new ResourceNotFoundException("Post message too long. Max length is 200.");
		}
	}
	
	@DeleteMapping("/post/{postId}")
	public ResponseEntity<?> deletePost(@PathVariable Long postId) {
		Post post = getById(postId);
		if(post != null) {
			postService.deletePost(post);
		}
		return ResponseEntity.ok().build();
	}
	
	private Post getById(Long postId) {
		return postService.getById(postId);
	}
	
	private Boolean validatePost(Post post) {
		if(post.getMessage().length()<=200) {
			return true;
		}
		return false;
	}
}
