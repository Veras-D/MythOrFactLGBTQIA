package com.veras.mythOrFactLGBT.service;

import com.veras.mythOrFactLGBT.model.User;
import com.veras.mythOrFactLGBT.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password123");
    }

    @Test
    void registerUser_success() {
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setId(1L);
            return savedUser;
        });

        User registeredUser = userService.registerUser(user);

        assertNotNull(registeredUser);
        assertEquals("encodedPassword", registeredUser.getPassword());
        assertEquals(1L, registeredUser.getId());
        assertEquals("USER", registeredUser.getRole());
        verify(userRepository).save(user);
    }

    @Test
    void registerUser_usernameExists() {
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(user);
        });
        assertEquals("Username already exists", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void findByUsername_found() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        Optional<User> foundUser = userService.findByUsername("testuser");
        assertTrue(foundUser.isPresent());
        assertEquals("testuser", foundUser.get().getUsername());
    }

    @Test
    void findByUsername_notFound() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        Optional<User> foundUser = userService.findByUsername("testuser");
        assertFalse(foundUser.isPresent());
    }
}
