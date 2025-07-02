package com.veras.mythOrFactLGBT.controller;

import com.veras.mythOrFactLGBT.dto.LoginRequest;
import com.veras.mythOrFactLGBT.dto.RegisterRequest;
import com.veras.mythOrFactLGBT.model.User;
import com.veras.mythOrFactLGBT.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import com.veras.mythOrFactLGBT.security.JwtUtil;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
// @ActiveProfiles("test")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtUtil jwtUtil;

    @Test
    void registerUser_success() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("newuser");
        registerRequest.setEmail("new@example.com");
        registerRequest.setPassword("password123");

        User registeredUser = new User();
        registeredUser.setId(1L);
        registeredUser.setUsername("newuser");

        when(userService.registerUser(any(User.class))).thenReturn(registeredUser);

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().string("User registered successfully. ID: 1"));
    }

    @Test
    void registerUser_usernameExists() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("existinguser");
        registerRequest.setEmail("new@example.com");
        registerRequest.setPassword("password123");

        when(userService.registerUser(any(User.class))).thenThrow(new IllegalArgumentException("Username already exists"));

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Username already exists"));
    }

    @Test
    void authenticateUser_success() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("password");

        Authentication authentication = mock(Authentication.class);
        org.springframework.security.core.userdetails.User userDetails =
            new org.springframework.security.core.userdetails.User("testuser", "password", List.of());


        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);


        User appUser = new User();
        appUser.setId(1L);
        appUser.setUsername("testuser");
        when(userService.findByUsername("testuser")).thenReturn(Optional.of(appUser));

        when(jwtUtil.generateToken(authentication)).thenReturn("mocked.jwt.token");


        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mocked.jwt.token"))
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.username").value("testuser"));
    }
}
