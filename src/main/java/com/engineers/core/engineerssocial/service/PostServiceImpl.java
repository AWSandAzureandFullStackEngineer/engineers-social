package com.engineers.core.engineerssocial.service;

import com.engineers.core.engineerssocial.entity.Post;
import com.engineers.core.engineerssocial.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    @Override
    public Post createNewPost(Post post, UUID userId) throws Exception {

        return null;
    }

    @Override
    public Post deletePost(UUID postId, UUID userId) {
        return null;
    }

    @Override
    public List<Post> findPostByUserId(UUID userId) {
        return List.of();
    }

    @Override
    public List<Post> findAllPosts() {
        return List.of();
    }

    @Override
    public Post savedPost(UUID postId, UUID userId) {
        return null;
    }

    @Override
    public Post likePost(UUID postId, UUID userId) {
        return null;
    }
}
