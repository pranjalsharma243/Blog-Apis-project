package com.pranjal.blog.services;

import java.util.List;

import com.pranjal.blog.payload.PostDto;
import com.pranjal.blog.payload.PostResponse;

public interface PostService {
	
	//create post
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId); 
	
	//update post
	PostDto updatePost(PostDto postDto, Integer postId);
	
	//Delete post
	void deletePost(Integer postId);
	
	//Get All posts
	
	PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	
	//Get Single Post
	
	PostDto getPostById(Integer postId);
	
	//Get all Posts by category
	
	List<PostDto> getPostsByCategory(Integer categoryId);
	
	//Get all posts by user
	List<PostDto> getPostsByUser(Integer userId);
	//Search posts by keyword
	List<PostDto> searchPosts(String keyword);

}
