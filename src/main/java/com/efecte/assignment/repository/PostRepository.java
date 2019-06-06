package com.efecte.assignment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.efecte.assignment.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findAllByOrderByLastModifiedDateDesc();
}
