package com.efecte.assignment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.efecte.assignment.exception.ResourceNotFoundException;
import com.efecte.assignment.model.Post;
import com.efecte.assignment.repository.PostRepository;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepo;
	
	public List<Post> getAllPosts(){
		return postRepo.findAllByOrderByLastModifiedDateDesc();
	}
	
	public Post createPost(Post post) {
		return postRepo.save(post);
	}
	
	public Post getById(Long postId) {
		return postRepo.findById(postId).map(post -> {
			return post;
		}).orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + postId));
	}
	
	public Post updatePost(Post post) {
		return postRepo.save(post);
	}
	
	public void deletePost(Post post) {
		postRepo.delete(post);
	}
}
