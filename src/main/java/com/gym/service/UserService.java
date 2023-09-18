package com.gym.service;

import com.gym.entities.User;
import com.gym.entities.UserNotFoundException;
import com.gym.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

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
    // update user
    public User updateUser(Long userId, User updatedUser) throws UserNotFoundException {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        // Update user data
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(this.passwordEncoder.encode(updatedUser.getPassword()));

        return userRepository.save(existingUser);
    }
    // get the user by Id
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(()-> new
                UserNotFoundException("User not found"));
    }
    // delete a user
    public void deleteUser(Long userId)throws UserNotFoundException {
        User existingUser= userRepository.findById(userId).orElseThrow(()
                -> new UserNotFoundException("User not found"));
        userRepository.delete(existingUser);
    }

    // list of all user
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

