package com.engineers.core.engineerssocial.controller;

import com.engineers.core.engineerssocial.entity.Comment;
import com.engineers.core.engineerssocial.entity.User;
import com.engineers.core.engineerssocial.service.CommentService;
import com.engineers.core.engineerssocial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/1.0/comments")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    @Autowired
    public CommentController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    @PostMapping("/post/{postId}")
    public Comment createComment(@RequestBody Comment comment,
                                 @RequestHeader("Authorization") String jwt,
                                 @PathVariable("postId") Integer postId) throws Exception {

        User user = userService.findUserByJwt(jwt);

        return commentService.createComment(comment, postId, user.getId());
    }

    @PutMapping("/like/{commentId}")
    public Comment likeComment(@RequestHeader("Authorization") String jwt,
                                 @PathVariable("commentId") Integer commentId) throws Exception {

        User user = userService.findUserByJwt(jwt);

        return commentService.likeComment(commentId, user.getId());
    }
}
