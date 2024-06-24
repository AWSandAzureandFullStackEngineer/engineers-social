package com.engineers.core.engineerssocial.controller;

import com.engineers.core.engineerssocial.entity.Post;
import com.engineers.core.engineerssocial.entity.User;
import com.engineers.core.engineerssocial.response.ApiResponse;
import com.engineers.core.engineerssocial.service.PostService;
import com.engineers.core.engineerssocial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/1.0/posts")
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @Autowired
    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestHeader("Authorization") String jwt,
                                           @RequestBody Post post
                                          ) throws Exception {


        User requestUser = userService.findUserByJwt(jwt);
        Post createdPost = postService.createNewPost(post, requestUser.getId());
        return new ResponseEntity<>(createdPost, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable("postId") Integer postId,
                                                  @RequestHeader("Authorization") String jwt) throws Exception {

        User requestUser = userService.findUserByJwt(jwt);
        String message = postService.deletePost(postId, requestUser.getId());
        ApiResponse response = new ApiResponse(message, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> findPostByIdHandler(@PathVariable("postId") Integer postId) throws Exception {
        Post post = postService.findPostById(postId);
        return new ResponseEntity<>(post, HttpStatus.ACCEPTED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Post>> findUsersPost(@PathVariable("userId") Integer userId) {
        List<Post> posts = postService.findPostByUserId(userId);

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> findAllPosts() {
        List<Post> posts = postService.findAllPosts();

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PutMapping("/save/{postId}")
    public ResponseEntity<Post> updateSavedPost(@PathVariable("postId") Integer postId,
                                                @RequestHeader("Authorization") String jwt) throws Exception {

        User requestUser = userService.findUserByJwt(jwt);
        Post post = postService.savedPost(postId, requestUser.getId());

        return new ResponseEntity<>(post, HttpStatus.ACCEPTED);
    }

    @PutMapping("/like/{postId}")
    public ResponseEntity<Post> likePost(@PathVariable("postId") Integer postId,
                                         @RequestHeader("Authorization") String jwt) throws Exception {

        User requestUser = userService.findUserByJwt(jwt);
        Post post = postService.savedPost(postId, requestUser.getId());

        return new ResponseEntity<>(post, HttpStatus.ACCEPTED);
    }


}
