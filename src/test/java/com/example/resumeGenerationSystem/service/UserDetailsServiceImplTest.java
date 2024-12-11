package com.example.resumeGenerationSystem.service;
import com.example.resumeGenerationSystem.entity.Role;
import com.example.resumeGenerationSystem.entity.User;
import com.example.resumeGenerationSystem.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(null, "johnDoe", "password123", Role.USER, "john.doe@example.com");
        user.setUsername("john_doe");
        user.setPassword("password123");
        user.setRole(Role.USER);  // Assuming Role is an enum like USER, ADMIN
    }

    @Test
    void testLoadUserByUsername_Success() {
        // Mock the UserRepository to return a user when findByUsername is called
        when(userRepository.findByUsername("john_doe")).thenReturn(java.util.Optional.of(user));

        // Mock the authorities for the user
        UserDetails userDetails = userDetailsService.loadUserByUsername("john_doe");

        // Verify the results
        assertNotNull(userDetails);
        assertEquals("john_doe", userDetails.getUsername());
        assertEquals("password123", userDetails.getPassword());

        // Assert that authorities are correctly set
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        // Mock the UserRepository to return an empty Optional when user is not found
        when(userRepository.findByUsername("john_doe")).thenReturn(java.util.Optional.empty());

        // Call the method and expect an exception
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("john_doe"));
    }
}
