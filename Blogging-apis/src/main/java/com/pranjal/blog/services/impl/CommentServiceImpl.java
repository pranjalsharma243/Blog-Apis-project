package com.pranjal.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pranjal.blog.entities.Comment;
import com.pranjal.blog.entities.Post;
import com.pranjal.blog.exceptions.ResourceNotFoundException;
import com.pranjal.blog.payload.CommentDto;
import com.pranjal.blog.repository.CommentRepo;
import com.pranjal.blog.repository.PostRepo;
import com.pranjal.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
	private PostRepo postRepo;
    @Autowired
	private CommentRepo commentRepo;
    @Autowired
    private ModelMapper modelMapper;
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
       Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Post id", postId));
		Comment comment=this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment savedComment=this.commentRepo.save(comment);
       return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment com=this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "Comment Id", commentId));
		this.commentRepo.delete(com);
		
	}

}
