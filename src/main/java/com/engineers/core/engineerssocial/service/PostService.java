package com.engineers.core.engineerssocial.service;

import com.engineers.core.engineerssocial.entity.Post;

import java.util.List;


public interface PostService {
    Post createNewPost(Post post, Integer userId) throws Exception;
    String deletePost(Integer postId, Integer userId) throws Exception;
    Post findPostById(Integer postId) throws Exception;
    List<Post> findPostByUserId(Integer userId);
    List<Post> findAllPosts();
    Post savedPost(Integer postId, Integer userId) throws Exception;
    Post likePost(Integer postId, Integer userId) throws Exception;
}
