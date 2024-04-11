package com.cribhub.backend.security;

import com.cribhub.backend.domain.Customer;
import com.cribhub.backend.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void loadUserByUsername_UserExists() {
        // Arrange
        String email = "test@example.com";
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setPassword("password");
        when(customerRepository.findByEmail(email)).thenReturn(Optional.of(customer));

        // Act
        UserDetails result = customUserDetailsService.loadUserByUsername(email);

        // Assert
        assertEquals(email, result.getUsername());
        assertEquals(customer.getPassword(), result.getPassword());
        assertTrue(result.getAuthorities().isEmpty());
    }

    @Test
    void loadUserByUsername_UserDoesNotExist() {
        // Arrange
        String email = "test@example.com";
        when(customerRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername(email));
    }
}