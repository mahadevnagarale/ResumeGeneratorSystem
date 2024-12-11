package com.example.resumeGenerationSystem.repository;

import com.example.resumeGenerationSystem.entity.Role;
import com.example.resumeGenerationSystem.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        // Set up a sample user object
        user = new User(null, "johnDoe", "password123", Role.USER, "john.doe@example.com");
        user.setUsername("john_doe");
        user.setPassword("password123");
        user.setRole(Role.valueOf("USER"));  // Assuming the Role is an enum, adjust accordingly
    }

    @Test
    void testSaveUser() {
        // Save the user and verify it's saved
        User savedUser = userRepository.save(user);
        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());  // Assuming the ID is generated after saving
        assertEquals("john_doe", savedUser.getUsername());
    }
    @AfterEach
    void tearDown() {
        userRepository.deleteAll();  // Delete all users after each test
    }

    @Test
    void testSaveUser_DuplicateUsername() {
        // Save the first user
        userRepository.save(user);

        // Attempt to save a user with the same username
        User duplicateUser = new User(null, "johnDoe", "password123", Role.USER, "john.doe@example.com");
        duplicateUser.setUsername("john_doe");  // Same username as the first user
        duplicateUser.setPassword("password123");
        duplicateUser.setRole(Role.valueOf("USER"));

        assertThrows(DataIntegrityViolationException.class, () -> {
            userRepository.save(duplicateUser);  // This should throw a DataIntegrityViolationException due to duplicate username
        });
    }


    @Test
    void testFindByUsername() {
        // Save the user and find by username
        userRepository.save(user);
        Optional<User> foundUser = userRepository.findByUsername("john_doe");
        assertTrue(foundUser.isPresent());
        assertEquals("john_doe", foundUser.get().getUsername());
    }

    @Test
    void testFindById() {
        // Save the user and find by ID
        User savedUser = userRepository.save(user);
        Optional<User> foundUser = userRepository.findById(savedUser.getId());
        assertTrue(foundUser.isPresent());
        assertEquals(savedUser.getId(), foundUser.get().getId());
    }

    @Test
    void testFindByUsername_NotFound() {
        // Test that finding a user by a non-existing username returns empty
        Optional<User> foundUser = userRepository.findByUsername("non_existent_user");
        assertFalse(foundUser.isPresent());
    }

    @Test
    void testDeleteUser() {
        // Save the user and delete it
        User savedUser = userRepository.save(user);
        assertNotNull(savedUser.getId()); // Ensure the user was saved

        userRepository.deleteById(savedUser.getId());
        Optional<User> foundUser = userRepository.findById(savedUser.getId());
        assertFalse(foundUser.isPresent());  // The user should no longer be found
    }

    @Test
    void testDeleteUser_NotFound() {
        // Try to delete a non-existing user
        userRepository.deleteById(999L); // Assuming 999L doesn't exist in the database
        Optional<User> foundUser = userRepository.findById(999L);
        assertFalse(foundUser.isPresent()); // The user should not exist
    }
}
