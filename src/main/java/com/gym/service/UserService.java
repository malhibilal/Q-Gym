package com.gym.service;

import com.gym.entities.User;
import com.gym.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User createUser(User user) throws Exception {
        // Check if the user with the same email already exists
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new Exception("User already registered with this email.");
        }

        // Create a new user
        User newUser = new User();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setPhoneNumber(user.getPhoneNumber());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(this.passwordEncoder.encode(user.getPassword()));
        return userRepository.save(newUser);
    }

    public User loginUser(String email, String password) throws Exception {
        // Find the user by email
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new Exception("User not found.");
        }

        // Check if the password matches
        if (!user.getPassword().equals(password)) {
            throw new Exception("Invalid password.");
        }

        return user;
    }
}

