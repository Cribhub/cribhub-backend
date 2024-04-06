package com.cribhub.backend.controller;

import com.cribhub.backend.controllers.CustomerController;
import com.cribhub.backend.domain.Crib;
import com.cribhub.backend.domain.Customer;
import com.cribhub.backend.services.CribService;
import com.cribhub.backend.services.CustomerService;
import com.cribhub.backend.services.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.cribhub.backend.DTO.CustomerDTO;
import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class CustomerControllerTests {

    @InjectMocks
    CustomerController customerController;

    @Mock
    CustomerServiceImpl customerService;

    @Mock
    CribService cribService;

    @Mock
    PasswordEncoder passwordEncoder;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        customerController = new CustomerController(customerService, cribService);
        customerController.setPasswordEncoder(passwordEncoder);
    }

    @Test
    public void createCustomerTest() {
        Customer customer = new Customer();
        customer.setPassword("password");

        when(passwordEncoder.encode(customer.getPassword())).thenReturn("hashedPassword");
        when(customerService.createCustomer(customer)).thenReturn(customer);

        ResponseEntity<CustomerDTO> result = customerController.createCustomer(customer);

        assertNotNull(result);
        verify(customerService, times(1)).createCustomer(customer);
    }

    @Test
    public void getCustomerByIdTest() {
        Customer customer = new Customer();

        when(customerService.getCustomerById(1L)).thenReturn(customer);

        ResponseEntity<CustomerDTO> result = customerController.getCustomerById(1L);
        CustomerDTO expectedCustomerDTO = CustomerDTO.ConvertToDTO(customer);

        assertNotNull(result);
        assertThat(result.getBody()).isEqualToComparingFieldByField(expectedCustomerDTO);
        verify(customerService, times(1)).getCustomerById(1L);
    }

    @Test
    public void deleteCustomerTest() {
        doNothing().when(customerService).deleteCustomer(1L);

        ResponseEntity<String> result = customerController.deleteCustomer(1L);

        assertEquals(204, result.getStatusCodeValue());
        verify(customerService, times(1)).deleteCustomer(1L);
    }

    @Test
    public void joinCribTest() {
        Crib crib = new Crib();
        Customer customer = new Customer();

        when(cribService.getCribById(1L)).thenReturn(crib);
        when(customerService.getCustomerById(1L)).thenReturn(customer);
        when(cribService.saveCrib(crib)).thenReturn(crib);

        ResponseEntity<Crib> result = customerController.joinCrib(1L, 1L);

        assertNotNull(result);
        assertEquals(crib, result.getBody());
        verify(cribService, times(1)).getCribById(1L);
        verify(customerService, times(1)).getCustomerById(1L);
        verify(cribService, times(1)).saveCrib(crib);
    }

    @Test
    public void joinCribTest_NotFound() {
        // Test when crib is null
        when(cribService.getCribById(1L)).thenReturn(null);
        ResponseEntity<Crib> result = customerController.joinCrib(1L, 1L);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());

        // Reset mock to test when customer is null
        reset(cribService);

        // Test when customer is null
        Crib crib = new Crib();
        when(cribService.getCribById(1L)).thenReturn(crib);
        when(customerService.getCustomerById(1L)).thenReturn(null);
        result = customerController.joinCrib(1L, 1L);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }
}