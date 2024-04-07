package com.cribhub.backend.controller;

import com.cribhub.backend.controllers.LoginController;
import com.cribhub.backend.security.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class LoginControllerTests {

    @InjectMocks
    LoginController loginController;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    JwtTokenUtil jwtTokenUtil;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        loginController = new LoginController(authenticationManager, jwtTokenUtil);
    }

    @Test
    public void loginTest() {
        LoginController.LoginRequest loginRequest = new LoginController.LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password");

        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        )).thenReturn(authentication);

        when(authentication.getName()).thenReturn("testUser");

        when(jwtTokenUtil.createToken("testUser")).thenReturn("testToken");

        ResponseEntity<LoginController.LoginResponse> result = loginController.login(loginRequest);

        assertNotNull(result);
        assertEquals("testToken", result.getBody().token());
        verify(authenticationManager, times(1)).authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        verify(jwtTokenUtil, times(1)).createToken("testUser");
    }
}