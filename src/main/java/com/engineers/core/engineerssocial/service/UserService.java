package com.engineers.core.engineerssocial.service;

import com.engineers.core.engineerssocial.entity.User;

import java.util.List;

public interface UserService {

    User findByUserId(Integer userId) throws Exception;

    User findUserByEmail(String email);

    User followUser(Integer user1, Integer user2) throws Exception;

    User updateUser(User user, Integer userId) throws Exception;

    List<User> searchUser(String query);

    public User findUserByJwt(String jwt);
}
