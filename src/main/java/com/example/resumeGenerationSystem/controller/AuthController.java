package com.example.resumeGenerationSystem.controller;


import com.example.resumeGenerationSystem.entity.Role;
import com.example.resumeGenerationSystem.entity.User;
import com.example.resumeGenerationSystem.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    public String signup(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return "Username already exists!";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Default role is USER if not provided
        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }

        userRepository.save(user);
        return "User registered successfully!";
    }

    @GetMapping("/login")
    public String login() {
        return "Login successful!";
    }

    @GetMapping("/logout")
    public String logout() {
        return "Logout successful!";
    }
}
