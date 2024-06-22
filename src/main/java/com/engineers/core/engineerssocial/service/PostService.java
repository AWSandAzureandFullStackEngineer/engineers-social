package com.engineers.core.engineerssocial.service;

import com.engineers.core.engineerssocial.entity.Post;

import java.util.List;
import java.util.UUID;

public interface PostService {
    Post createNewPost(Post post, UUID userId) throws Exception;
    Post deletePost(UUID postId, UUID userId);
    List<Post> findPostByUserId(UUID userId);
    List<Post> findAllPosts();
    Post savedPost(UUID postId, UUID userId);
    Post likePost(UUID postId, UUID userId);
}
