package com.engineers.core.engineerssocial.service;

import com.engineers.core.engineerssocial.entity.Comment;
import com.engineers.core.engineerssocial.entity.Post;
import com.engineers.core.engineerssocial.entity.User;
import com.engineers.core.engineerssocial.repository.CommentRepository;
import com.engineers.core.engineerssocial.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private final PostService postService;
    private final UserService userService;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Autowired
    public CommentServiceImpl(PostService postService, UserService userService, CommentRepository commentRepository, PostRepository postRepository) {
        this.postService = postService;
        this.userService = userService;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
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
        post.getComments().add(savedComment);
        postRepository.save(post);
        return savedComment;
    }

    @Override
    public Comment findCommentById(Integer commentId) throws Exception {
        Optional<Comment> optional = commentRepository.findById(commentId);

        if(optional.isEmpty()) {
            throw new Exception("Comment does not exist.");
        }

        return optional.get();
    }

    @Override
    public Comment likeComment(Integer commentId, Integer userId) throws Exception {
        Comment comment = findCommentById(commentId);
        User user = userService.findByUserId(userId);
        if (!comment.getLiked().contains(user)) {
            comment.getLiked().add(user);
        } else {
            comment.getLiked().remove(user);
        }
        return commentRepository.save(comment);
    }
}
