package com.cribhub.backend.services;

import com.cribhub.backend.controllers.exceptions.CustomerNotFoundException;
import com.cribhub.backend.controllers.exceptions.EmailAlreadyInUseException;
import com.cribhub.backend.controllers.exceptions.UsernameAlreadyTakenException;
import com.cribhub.backend.domain.Customer;
import com.cribhub.backend.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CustomerServiceTests {

    @InjectMocks
    CustomerServiceImpl customerService;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    CustomerRepository customerRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getCustomerByIdTest() throws CustomerNotFoundException {
        Customer customer = new Customer();
        customer.setUserId(1L);
        customer.setUserName("Test Customer");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Customer found = customerService.getCustomerById(1L);

        assertEquals(customer.getUserName(), found.getUserName());
        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    public void createCustomerTest() throws EmailAlreadyInUseException, UsernameAlreadyTakenException {
        Customer customer = new Customer();
        customer.setUserName("Test Customer");

        when(customerRepository.save(customer)).thenReturn(customer);
        when(passwordEncoder.encode(customer.getPassword())).thenReturn("encodedPassword");

        Customer saved = customerService.createCustomer(customer);

        assertEquals(customer.getUserName(), saved.getUserName());
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    public void updateCustomerTest() throws CustomerNotFoundException {
        Customer customer = new Customer();
        customer.setUserId(1L);
        customer.setEmail("test@example.com");

        Customer updatedCustomer = new Customer();
        updatedCustomer.setEmail("updated@example.com");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenAnswer(i -> i.getArguments()[0]);

        Customer result = customerService.updateCustomer(1L, updatedCustomer);

        assertEquals(updatedCustomer.getEmail(), result.getEmail());
        verify(customerRepository, times(1)).findById(1L);
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    public void updateNonExistentCustomerTest() {
        Customer updatedCustomer = new Customer();
        updatedCustomer.setEmail("updated@example.com");

        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () -> {
            customerService.updateCustomer(1L, updatedCustomer);
        });

        assertEquals("Customer with id 1 not found", exception.getMessage());

        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    public void deleteCustomerTest() throws CustomerNotFoundException {
        Customer customer = new Customer();
        customer.setUserId(1L);
        customer.setUserName("Test Customer");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        doNothing().when(customerRepository).delete(customer);

        customerService.deleteCustomer(1L);

        verify(customerRepository, times(1)).deleteById(1L);
    }
}