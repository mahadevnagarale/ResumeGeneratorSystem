package com.example.resumeGenerationSystem.service;

import com.example.resumeGenerationSystem.entity.Role;
import com.example.resumeGenerationSystem.entity.User;
import com.example.resumeGenerationSystem.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize a sample user object
        user = new User(null, "johnDoe", "password123", Role.USER, "john.doe@example.com");
        user.setId(1L);
        user.setUsername("john_doe");
        user.setPassword("password123");
        user.setRole(Role.valueOf("USER"));
    }

    @Test
    void testSaveUser() {
        // Mock the save method to return the user
        when(userRepository.save(user)).thenReturn(user);

        // Call the save method
        User savedUser = userService.saveUser(user);

        // Verify the result
        assertNotNull(savedUser);
        assertEquals("john_doe", savedUser.getUsername());

        // Verify that the save method was called once
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testFindUserByUsername() {
        // Mock the findByUsername method to return the user
        when(userRepository.findByUsername("john_doe")).thenReturn(Optional.of(user));

        // Call the find method
        Optional<User> foundUser = userService.findUserByUsername("john_doe");

        // Verify the result
        assertTrue(foundUser.isPresent());
        assertEquals("john_doe", foundUser.get().getUsername());

        // Verify that the findByUsername method was called once
        verify(userRepository, times(1)).findByUsername("john_doe");
    }

    @Test
    void testFindUserById() {
        // Mock the findById method to return the user
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Call the find method
        Optional<User> foundUser = userService.findUserById(1L);

        // Verify the result
        assertTrue(foundUser.isPresent());
        assertEquals("john_doe", foundUser.get().getUsername());

        // Verify that the findById method was called once
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateUser() {
        // Mock the findById method to return the existing user
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Create an updated user object
        User updatedUser = new User(null, "johnDoe", "password123", Role.USER, "john.doe@example.com");
        updatedUser.setUsername("john_doe_updated");
        updatedUser.setPassword("newpassword123");
        updatedUser.setRole(Role.ADMIN);  // Assuming Role is an enum

        // Mock the save method to return the updated user
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        // Call the update method
        User updatedUserResult = userService.updateUser(1L, updatedUser);

        // Verify the result
        assertNotNull(updatedUserResult); // Ensure the result is not null
        assertEquals("john_doe_updated", updatedUserResult.getUsername());
        assertEquals(Role.ADMIN, updatedUserResult.getRole());  // Compare with the enum constant

        // Verify that the save method was called once
        verify(userRepository, times(1)).save(any(User.class));
    }



    @Test
    void testUpdateUser_NotFound() {
        // Mock the findById method to return empty
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Create an updated user object
        User updatedUser = new User(null, "johnDoe", "password123", Role.USER, "john.doe@example.com");
        updatedUser.setUsername("john_doe_updated");

        // Call the update method and expect a RuntimeException
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.updateUser(1L, updatedUser);
        });

        // Verify the exception message
        assertEquals("User not found with id: 1", exception.getMessage());

        // Verify that save was never called
        verify(userRepository, times(0)).save(updatedUser);
    }

    @Test
    void testDeleteUser() {
        // Mock the existsById and deleteById methods
        when(userRepository.existsById(1L)).thenReturn(true);

        // Call the delete method
        boolean isDeleted = userService.deleteUser(1L);

        // Verify the result
        assertTrue(isDeleted);

        // Verify that the deleteById method was called once
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUser_NotFound() {
        // Mock the existsById method to return false
        when(userRepository.existsById(1L)).thenReturn(false);

        // Call the delete method
        boolean isDeleted = userService.deleteUser(1L);

        // Verify the result
        assertFalse(isDeleted);

        // Verify that deleteById was never called
        verify(userRepository, times(0)).deleteById(1L);
    }

    @Test
    void testGetAllUsers() {
        // Mock the findAll method to return a list of users
        when(userRepository.findAll()).thenReturn(List.of(user));

        // Call the method
        List<User> users = userService.getAllUsers();

        // Verify the result
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("john_doe", users.get(0).getUsername());

        // Verify that findAll was called once
        verify(userRepository, times(1)).findAll();
    }
}
