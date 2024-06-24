package com.engineers.core.engineerssocial.service;

import com.engineers.core.engineerssocial.entity.Comment;
import com.engineers.core.engineerssocial.entity.Post;
import com.engineers.core.engineerssocial.entity.User;
import com.engineers.core.engineerssocial.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentServiceImpl implements CommentService {
    private final PostService postService;
    private final UserService userService;
    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(PostService postService, UserService userService, CommentRepository commentRepository) {
        this.postService = postService;
        this.userService = userService;
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment createComment(Comment comment,
                                 Integer postId,
                                 Integer userId) throws Exception {

        User user = userService.findByUserId(userId);
        Post post = postService.findPostById(postId);
        comment.setUser(user);
        comment.setContent(comment.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        Comment savedComment = commentRepository.save(comment);
        return null;
    }

    @Override
    public Comment findCommentById(Integer commentId) {
        return null;
    }

    @Override
    public Comment likeComment(Integer commentId, Integer userId) {
        return null;
    }
}
