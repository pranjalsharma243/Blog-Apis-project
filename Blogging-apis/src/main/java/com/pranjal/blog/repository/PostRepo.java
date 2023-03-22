package com.pranjal.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pranjal.blog.entities.Category;
import com.pranjal.blog.entities.Post;
import com.pranjal.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
    
	List<Post> findByTitleContaining(String title);
}
