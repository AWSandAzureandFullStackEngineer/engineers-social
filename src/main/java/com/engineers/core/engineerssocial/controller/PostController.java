package com.engineers.core.engineerssocial.controller;

import com.engineers.core.engineerssocial.entity.Post;
import com.engineers.core.engineerssocial.response.ApiResponse;
import com.engineers.core.engineerssocial.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/1.0/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<Post> createPost(@RequestBody Post post, @PathVariable("userId") Integer userId) throws Exception {
        Post createdPost = postService.createNewPost(post, userId);
        return new ResponseEntity<>(createdPost, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/posts/{postId}/user/{userId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable("postId") Integer postId,
                                                  @PathVariable("userId") Integer userId) throws Exception {
        String message = postService.deletePost(postId, userId);
        ApiResponse response = new ApiResponse(message, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
