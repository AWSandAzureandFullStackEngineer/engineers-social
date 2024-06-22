package com.engineers.core.engineerssocial.controller;

import com.engineers.core.engineerssocial.entity.User;
import com.engineers.core.engineerssocial.repository.UserRepository;
import com.engineers.core.engineerssocial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/1.0/users")
public class UserController {

    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PutMapping("/{userId}")
    public User updateUser(@RequestBody User user, @PathVariable Integer userId) throws Exception {
        return userService.updateUser(user, userId);
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable("userId") Integer id) throws Exception {
        return userService.findByUserId(id);
    }

    @PutMapping("/follow/{userId1}/{userId2}")
    public User followUserHandler(@PathVariable Integer userId1, @PathVariable Integer userId2) throws Exception {
        return userService.followUser(userId1, userId2);
    }

    @GetMapping("/users/search")
    public List<User> searchUser(@RequestParam("query") String query) {
        return userService.searchUser(query);
    }
}
