package com.doomsdaysale.controller;

import com.doomsdaysale.model.User;
import com.doomsdaysale.repository.UserRepository;
import com.doomsdaysale.response.SignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Rest-controllers are used to make a class web request handler. Rest controller is the combination of controller and requestbody annotation.
@RestController
@RequestMapping("/auth")
//@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @PostMapping("/signup") // as we are posting data we need to use PostMapping
    public ResponseEntity<User> createUserHandler(@RequestBody SignupRequest req){
        // create a new user and set the email and full name
        User user = new User();
        user.setEmail(req.getEmail());
        user.setFullName(req.getFullName());

        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }
}
