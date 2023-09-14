package com.gym.controller;

import com.gym.entities.User;
import com.gym.repository.UserRepository;
import com.gym.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<User>registerUser(@RequestBody User user) throws Exception {
        System.out.println("Received user: " + user.toString());
        User registerUser = userService.createUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(registerUser);

    }
    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User user)throws Exception{
        User loggedUser =userService.loginUser(user.getEmail(), user.getPassword());
        return ResponseEntity.ok(loggedUser);
    }
}
