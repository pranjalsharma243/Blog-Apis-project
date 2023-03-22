package com.pranjal.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pranjal.blog.entities.User;

public interface UserRepo extends JpaRepository<User,Integer>{
	

}
