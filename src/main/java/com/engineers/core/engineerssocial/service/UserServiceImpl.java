package com.engineers.core.engineerssocial.service;

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
    public User registerUser(User user) {
        User newUser = new User();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setTypeOfEngineer(user.getTypeOfEngineer());
        return userRepository.save(newUser);
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
    public User followUser(Integer userId1, Integer userId2) throws Exception {
        User user1 = findByUserId(userId1);
        User user2 = findByUserId(userId2);
        user2.getFollowers().add(user1.getId());

        userRepository.save(user1);
        userRepository.save(user2);
        return user1;
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


}
