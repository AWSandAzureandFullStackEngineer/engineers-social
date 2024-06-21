package com.engineers.core.engineerssocial.controller;

import com.engineers.core.engineerssocial.config.JwtProvider;
import com.engineers.core.engineerssocial.entity.User;
import com.engineers.core.engineerssocial.repository.UserRepository;
import com.engineers.core.engineerssocial.response.AuthResponse;
import com.engineers.core.engineerssocial.service.NewUserDetailImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final NewUserDetailImplementation newUserDetailImplementation;

    @Autowired
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, NewUserDetailImplementation newUserDetailImplementation) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.newUserDetailImplementation = newUserDetailImplementation;
    }

    @PostMapping("/register")
    public ResponseEntity<User> createUserHandler(@RequestBody User user) throws Exception {
        User doesUserExist = userRepository.findByUsername(user.getUsername());

        if (doesUserExist != null) {
            throw new Exception("username already exist");
        }

        User createNewUser = new User();
        createNewUser.setPassword(passwordEncoder.encode(user.getPassword()));
        createNewUser.setFirstName(user.getFirstName());
        createNewUser.setLastName(user.getLastName());
        createNewUser.setUsername(user.getUsername());
        createNewUser.setEmail(user.getEmail());
        createNewUser.setTypeOfEngineer(user.getTypeOfEngineer());

        User saveUser = userRepository.save(createNewUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

        String jwt = JwtProvider.generationToken(authentication);

        AuthResponse response = new AuthResponse();
        response.setMessage("Registration successful");
        response.setJwt(jwt);

        return new ResponseEntity<>(saveUser, HttpStatus.CREATED);
    }
}
