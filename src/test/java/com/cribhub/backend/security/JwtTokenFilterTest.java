package com.cribhub.backend.security;

import com.cribhub.backend.security.CustomUserDetailsService;
import com.cribhub.backend.security.JwtTokenFilter;
import com.cribhub.backend.security.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtTokenFilterTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @Mock
    private Log mockLogger;

    @InjectMocks
    private JwtTokenFilter jwtTokenFilter;

    @Test
    void doFilterInternal_UserDoesNotExist() throws Exception {
        // Arrange
        String token = "Bearer token";
        when(request.getHeader("Authorization")).thenReturn(token);
        when(jwtTokenUtil.extractUsername(token.substring(7))).thenReturn(null);

        // Act
        jwtTokenFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_ExceptionThrown_ErrorLogged() throws Exception {
        // Arrange
        String token = "Bearer token";
        RuntimeException exception = new RuntimeException("Test exception");
        when(request.getHeader("Authorization")).thenReturn(token);
        when(jwtTokenUtil.extractUsername(token.substring(7))).thenThrow(exception);

        // Replace the real logger in JwtTokenFilter with the mock logger
        ReflectionTestUtils.setField(jwtTokenFilter, "logger", mockLogger);

        // Act
        jwtTokenFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(mockLogger).error("An error occurred during JWT Token validation.", exception);
    }
}