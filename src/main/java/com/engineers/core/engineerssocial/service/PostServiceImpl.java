package com.engineers.core.engineerssocial.service;

import com.engineers.core.engineerssocial.entity.Post;
import com.engineers.core.engineerssocial.entity.User;
import com.engineers.core.engineerssocial.repository.PostRepository;
import com.engineers.core.engineerssocial.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserService userService, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }


    @Override
    public Post createNewPost(Post post, Integer userId) throws Exception {
        User user = userService.findByUserId(userId);
        Post newPost = new Post();
        newPost.setTitle(post.getTitle());
        newPost.setContent(post.getContent());
        newPost.setImage(post.getImage());
        newPost.setUser(user);
        return newPost;
    }

    @Override
    public String deletePost(Integer postId, Integer userId) throws Exception{
        Post post = findPostById(userId);
        User user = userService.findByUserId(userId);
        if (post.getUser().getId() != user.getId()) {
            throw new Exception("Cannot delete post");
        }
        postRepository.delete(post);
        return "Post deleted successfully";
    }

    @Override
    public Post findPostById(Integer postId) throws Exception {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            throw new Exception("Post with id: " + postId + "does not exist");
        }
        return post.get();
    }

    @Override
    public List<Post> findPostByUserId(Integer userId) {
        return postRepository.findPostByUserId(userId);
    }

    @Override
    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post savedPost(Integer postId, Integer userId) throws Exception {
        Post post = findPostById(postId);
        User user = userService.findByUserId(userId);

        if (user.getSavedPost().add(post)) {
            user.getSavedPost().remove(post);
        } else {
            user.getSavedPost().add(post);
        }
        userRepository.save(user);
        return post;
    }

    @Override
    public Post likePost(Integer postId, Integer userId) throws Exception {
        Post post = findPostById(postId);
        User user = userService.findByUserId(userId);

        if (post.getLiked().contains(user)) {
            post.getLiked().remove(user);
        } else {
            post.getLiked().add(user);
        }

        post.getLiked().add(user);
        return postRepository.save(post);
    }
}
