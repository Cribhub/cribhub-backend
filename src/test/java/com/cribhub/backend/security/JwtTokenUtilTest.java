package com.cribhub.backend.security;

import com.cribhub.backend.domain.Customer;
import com.cribhub.backend.repositories.CustomerRepository;
import com.cribhub.backend.security.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtTokenUtilTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private JwtTokenUtil jwtTokenUtil;

    @Test
    void createToken_UserExists() {
        // Arrange
        String email = "test@example.com";
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setUserId(1L);
        customer.setUserName("Test User");
        when(customerRepository.findByEmail(email)).thenReturn(customer);

        // Act
        String result = jwtTokenUtil.createToken(email);

        // Assert
        assertNotNull(result);
    }

    @Test
    void createToken_UserDoesNotExist() {
        // Arrange
        String email = "test@example.com";
        when(customerRepository.findByEmail(email)).thenReturn(null);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> jwtTokenUtil.createToken(email));
    }

    @Test
    void extractUsername() {
        // Arrange
        String email = "test@example.com";
        Customer customer = new Customer();
        customer.setEmail(email);
        when(customerRepository.findByEmail(email)).thenReturn(customer);
        String token = jwtTokenUtil.createToken(email);

        // Act
        String result = jwtTokenUtil.extractUsername(token);

        // Assert
        assertEquals(email, result);
    }

    @Test
    void extractClaim() {
        // Arrange
        String email = "test@example.com";
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setUserId(1L);
        customer.setUserName("Test User");
        when(customerRepository.findByEmail(email)).thenReturn(customer);
        String token = jwtTokenUtil.createToken(email);

        // Act
        Long result = jwtTokenUtil.extractClaim(token, claims -> claims.get("customerId", Long.class));

        // Assert
        assertEquals(customer.getUserId(), result);
    }

    @Test
    void extractAllClaims() {
        // Arrange
        String email = "test@example.com";
        Customer customer = new Customer();
        customer.setEmail(email);
        when(customerRepository.findByEmail(email)).thenReturn(customer);
        String token = jwtTokenUtil.createToken(email);

        // Act
        Claims result = jwtTokenUtil.extractAllClaims(token);

        // Assert
        assertEquals(email, result.getSubject());
    }

    @Test
    void isTokenExpired() {
        // Arrange
        String email = "test@example.com";
        Customer customer = new Customer();
        customer.setEmail(email);
        when(customerRepository.findByEmail(email)).thenReturn(customer);
        String token = jwtTokenUtil.createToken(email);

        // Act
        Boolean result = jwtTokenUtil.isTokenExpired(token);

        // Assert
        assertFalse(result);
    }

    @Test
    void extractExpiration() {
        // Arrange
        String email = "test@example.com";
        Customer customer = new Customer();
        customer.setEmail(email);
        when(customerRepository.findByEmail(email)).thenReturn(customer);
        String token = jwtTokenUtil.createToken(email);

        // Act
        Date result = jwtTokenUtil.extractExpiration(token);

        // Assert
        assertNotNull(result);
    }

    @Test
    void validateToken() {
        // Arrange
        String email = "test@example.com";
        Customer customer = new Customer();
        customer.setEmail(email);
        when(customerRepository.findByEmail(email)).thenReturn(customer);
        String token = jwtTokenUtil.createToken(email);
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(email);

        // Act
        Boolean result = jwtTokenUtil.validateToken(token, userDetails);

        // Assert
        assertTrue(result);
    }
}