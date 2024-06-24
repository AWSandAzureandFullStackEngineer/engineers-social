package com.engineers.core.engineerssocial.service;

import com.engineers.core.engineerssocial.entity.Comment;

public interface CommentService {
   Comment createComment(Comment comment, Integer postId, Integer userId) throws Exception;

   Comment findCommentById(Integer commentId);
   Comment likeComment(Integer commentId, Integer userId);
}
