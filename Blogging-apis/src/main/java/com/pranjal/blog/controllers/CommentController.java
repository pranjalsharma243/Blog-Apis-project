package com.pranjal.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pranjal.blog.payload.ApiResponse;
import com.pranjal.blog.payload.CommentDto;
import com.pranjal.blog.services.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {
	@Autowired
	private CommentService commentService;

	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, 
			@PathVariable Integer postId) {

		CommentDto comments = this.commentService.createComment(commentDto, postId);

		return new ResponseEntity<CommentDto>(comments, HttpStatus.CREATED);

	}

	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId) {
		this.commentService.deleteComment(commentId);
		ApiResponse deleted = new ApiResponse("Comments has been deleted Successfully!!", true);
		return new ResponseEntity<ApiResponse>(deleted, HttpStatus.OK);
	}

}
