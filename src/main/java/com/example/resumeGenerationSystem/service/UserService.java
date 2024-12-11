package com.example.resumeGenerationSystem.service;

import com.example.resumeGenerationSystem.entity.User;
import com.example.resumeGenerationSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Save a new user to the database
    public User saveUser(User user) {
        // Check if the username already exists
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Username '" + user.getUsername() + "' is already taken.");
        }

        System.out.println("Saving user: " + user); // Debugging line

        // Save and return the new user
        return userRepository.save(user);
    }


    // Find a user by their username
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Find a user by their ID
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    // Update user information
    public User updateUser(Long id, User updatedUser) {
        Optional<User> existingUserOpt = userRepository.findById(id);
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();

            // Check if the username has changed and if the new username already exists
            if (!existingUser.getUsername().equals(updatedUser.getUsername())) {
                Optional<User> userWithNewUsername = userRepository.findByUsername(updatedUser.getUsername());
                if (userWithNewUsername.isPresent()) {
                    // Return a meaningful exception message if the new username is taken
                    throw new IllegalArgumentException("Username '" + updatedUser.getUsername() + "' is already taken.");
                }
            }

            // Update user fields and save the changes
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setRole(updatedUser.getRole());
            return userRepository.save(existingUser);
        } else {
            // Handle the case where the user does not exist
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    // Delete a user by ID
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        // Return false if user with the given ID doesn't exist
        return false;
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll(); // Fetch all users from the database
    }
}
