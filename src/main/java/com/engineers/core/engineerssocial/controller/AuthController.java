package com.engineers.core.engineerssocial.controller;

import com.engineers.core.engineerssocial.config.JwtProvider;
import com.engineers.core.engineerssocial.entity.User;
import com.engineers.core.engineerssocial.repository.UserRepository;
import com.engineers.core.engineerssocial.request.LoginRequest;
import com.engineers.core.engineerssocial.response.AuthResponse;
import com.engineers.core.engineerssocial.service.NewUserDetailImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"https://backendapi.victoriousmushroom-5e574b43.centralus.azurecontainerapps.io", "http//:localhost:5173"})
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
    public ResponseEntity<AuthResponse> registerUser(@RequestBody User user) throws Exception {
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

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = JwtProvider.generationToken(authentication);

        AuthResponse response = new AuthResponse();
        response.setMessage("Registration successful");
        response.setJwt(jwt);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = JwtProvider.generationToken(authentication);

        AuthResponse response = new AuthResponse();
        response.setMessage("Login successful");
        response.setJwt(jwt);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = newUserDetailImplementation.loadUserByUsername(username);
        if (userDetails == null) {
            throw new BadCredentialsException("invalid username");
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
