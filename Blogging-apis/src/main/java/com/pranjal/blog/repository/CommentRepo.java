package com.pranjal.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pranjal.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{
 
}
