package com.pranjal.blog.services.impl;



import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import  org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pranjal.blog.entities.Category;
import com.pranjal.blog.entities.Post;
import com.pranjal.blog.entities.User;
import com.pranjal.blog.exceptions.ResourceNotFoundException;
import com.pranjal.blog.payload.PostDto;
import com.pranjal.blog.payload.PostResponse;
import com.pranjal.blog.repository.CategoryRepo;
import com.pranjal.blog.repository.PostRepo;
import com.pranjal.blog.repository.UserRepo;
import com.pranjal.blog.services.PostService;
@Service
public class PostServiceImpl implements PostService{
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;
	
	
	//create post but for that its important to have the category id and the user id for which we have to create the post
	@Override
	public PostDto createPost(PostDto postDto,Integer userId, Integer categoryId) {
		User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User ","user id",userId));
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category ","category id",categoryId));
		Post post=this.modelMapper.map(postDto, Post.class);
		post.setImageName("Default.png");
		post.setAddDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		Post newPost=this.postRepo.save(post);
		PostDto updatedPostDto=this.modelMapper.map(newPost, PostDto.class);
		return updatedPostDto;
	}
	
	
	
	//Update Post by Id
	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
	Post post=	this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "post id", postId));
	  post.setTitle(postDto.getTitle());
	  post.setContent(postDto.getContent());
	  post.setImageName(postDto.getImageName());
	  Post updatedPost=this.postRepo.save(post);
	  PostDto updated=this.modelMapper.map(updatedPost,PostDto.class);
	  return updated;
	}
	
	
	
    //Delete Post by ID
	@Override
	public void deletePost(Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Post id", postId));
	    this.postRepo.delete(post);
	}
	
	
	
	
    //Get All Posts
	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		Sort sort=null;
		if(sortDir.equalsIgnoreCase("asc"))
		{
			sort=Sort.by(sortBy).ascending();
		}
		else
		{
			sort=Sort.by(sortBy).descending();
		}
	   Pageable p=PageRequest.of(pageNumber, pageSize,sort );
		Page<Post> pagePost=this.postRepo.findAll(p);
		List<Post> allPosts=pagePost.getContent();
		List<PostDto> postDtos=allPosts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}
	
	
	
    // Get Post by Id
	@Override
	public PostDto getPostById(Integer postId) {
	Post post=	this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","post id",postId));
	PostDto updatedPostDto=	this.modelMapper.map(post, PostDto.class);
	return updatedPostDto;
	}
	
	
	
    // Get all posts by category
	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "category id", categoryId));
		List<Post> posts=this.postRepo.findByCategory(cat);
		List<PostDto> postDtos=posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}
	
	
	
    //  Get all posts by user
	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "user id", userId));
		List<Post> posts=this.postRepo.findByUser(user);
		List<PostDto> postDtos=posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	
	
	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts=this.postRepo.findByTitleContaining(keyword);
		List<PostDto> postdtos=posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postdtos;
	}
	

}
