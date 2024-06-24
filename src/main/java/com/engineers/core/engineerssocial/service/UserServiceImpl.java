package com.engineers.core.engineerssocial.service;

import com.engineers.core.engineerssocial.config.JwtProvider;
import com.engineers.core.engineerssocial.entity.User;
import com.engineers.core.engineerssocial.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User findByUserId(Integer userId) throws Exception{
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new Exception("User does not exist with Id: " + userId);
        }

    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User followUser(Integer requestUserId, Integer userId2) throws Exception {
        User requestUser = findByUserId(requestUserId);

        User user2 = findByUserId(userId2);

        user2.getFollowers().add(requestUser.getId());
        requestUser.getFollowing().add(user2.getId());

        userRepository.save(requestUser);
        userRepository.save(user2);
        return requestUser;
    }

    @Override
    public User updateUser(User user, Integer userId) throws Exception{
        Optional<User> user1 = userRepository.findById(userId);

        if (user1.isEmpty()) {
            throw new Exception("User does not exist: " + userId);
        }

        User savedUser = user1.get();

        if(user.getFirstName() != null) {
            savedUser.setFirstName(user.getFirstName());
        }

        if (user.getLastName() != null) {
            savedUser.setLastName(user.getLastName());
        }

        if (user.getEmail() != null) {
            savedUser.setEmail(user.getEmail());
        }

        if (user.getTypeOfEngineer() != null) {
            savedUser.setTypeOfEngineer(user.getTypeOfEngineer());
        }

        return userRepository.save(savedUser);
    }

    @Override
    public List<User> searchUser(String query) {
        return userRepository.searchUser(query);
    }

    @Override
    public User findUserByJwt(String jwt) {
        String username = JwtProvider.getUsernameFromToken(jwt);
        return userRepository.findByUsername(username);
    }


}
