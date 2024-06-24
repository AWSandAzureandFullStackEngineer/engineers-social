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


    @GetMapping("/{userId}")
    public User getUserById(@PathVariable("userId") Integer id) throws Exception {
        return userService.findByUserId(id);
    }

    @PutMapping("/users/follow/{userId2}")
    public User followUserHandler(@RequestHeader("Authorization") String jwt, @PathVariable Integer userId2) throws Exception {
        User requestUser = userService.findUserByJwt(jwt);
        return userService.followUser(requestUser.getId(), userId2);
    }

    @GetMapping("/users/search")
    public List<User> searchUser(@RequestParam("query") String query) {
        return userService.searchUser(query);
    }

    @PutMapping("/auth-users")
    public User updateUser(@RequestHeader("Authorization") String jwt, @RequestBody User user) throws Exception {
        User requestUser = userService.findUserByJwt(jwt);

        return userService.updateUser(user, requestUser.getId());
    }

    @GetMapping("/api/users/profile")
    public User getUserFromToken(@RequestHeader("Authorization") String jwt) {
        User user = userService.findUserByJwt(jwt);
        user.setPassword(null);
        return user;
    }
}
