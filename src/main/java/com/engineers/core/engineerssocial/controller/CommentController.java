package com.engineers.core.engineerssocial.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/1.0/comments")
public class CommentController {
    @GetMapping("/test")
    public String testing(String greeting) {
        greeting = "hi from comments test";
        return greeting;
    }
}