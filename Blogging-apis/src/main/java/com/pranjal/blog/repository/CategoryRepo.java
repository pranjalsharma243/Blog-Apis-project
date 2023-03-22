package com.pranjal.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pranjal.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
