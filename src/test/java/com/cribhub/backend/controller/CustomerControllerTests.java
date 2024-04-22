package com.cribhub.backend.controller;

import com.cribhub.backend.controllers.CustomerController;
import com.cribhub.backend.domain.Crib;
import com.cribhub.backend.domain.Customer;
import com.cribhub.backend.dto.CustomerDTO;
import com.cribhub.backend.exceptions.CribNotFoundException;
import com.cribhub.backend.exceptions.CustomerNotFoundException;
import com.cribhub.backend.exceptions.EmailAlreadyInUseException;
import com.cribhub.backend.exceptions.UsernameAlreadyTakenException;
import com.cribhub.backend.exceptions.CribNameAlreadyTakenException;
import com.cribhub.backend.services.CustomerServiceImpl;
import com.cribhub.backend.services.intefaces.CribService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
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

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        customerController = new CustomerController(customerService, cribService);
    }

    @Test
    public void createCustomerTest() throws EmailAlreadyInUseException, UsernameAlreadyTakenException {
        Customer customer = new Customer();
        customer.setPassword("password");

        when(customerService.createCustomer(customer)).thenReturn(customer);

        ResponseEntity<CustomerDTO> result = customerController.createCustomer(customer);

        assertNotNull(result);
        verify(customerService, times(1)).createCustomer(customer);
    }

    @Test
    public void getCustomerByIdTest() throws CustomerNotFoundException {
        Customer customer = new Customer();

        when(customerService.getCustomerById(1L)).thenReturn(customer);

        ResponseEntity<CustomerDTO> result = customerController.getCustomerById(1L);
        CustomerDTO expectedCustomerDTO = CustomerDTO.ConvertToDTO(customer);

        assertNotNull(result);
        assertThat(result.getBody()).isEqualToComparingFieldByField(expectedCustomerDTO);
        verify(customerService, times(1)).getCustomerById(1L);
    }

    @Test
    public void deleteCustomerTest() throws CustomerNotFoundException, CribNotFoundException {
        doNothing().when(customerService).deleteCustomer(1L);

        ResponseEntity<String> result = customerController.deleteCustomer(1L);

        assertEquals(204, result.getStatusCodeValue());
        verify(customerService, times(1)).deleteCustomer(1L);
    }

    @Test
    public void joinCribTest() throws CustomerNotFoundException, CribNotFoundException, CribNameAlreadyTakenException {
        Crib crib = new Crib();
        Customer customer = new Customer();

        when(cribService.getCribById(1L)).thenReturn(crib);
        when(customerService.getCustomerById(1L)).thenReturn(customer);
        when(cribService.saveCrib(crib)).thenReturn(crib);

        ResponseEntity<Void> result = customerController.joinCrib(1L, 1L);

        assertEquals(HttpStatusCode.valueOf(200), result.getStatusCode());
        verify(cribService, times(1)).addMember(1L, 1L);
    }

    @Test
    public void joinCribTest_CustomerNotFound()
            throws CustomerNotFoundException, CribNotFoundException, CribNameAlreadyTakenException {
        // Test when customer not found
        doThrow(new CustomerNotFoundException(1L)).when(cribService).addMember(1L, 1L);

        assertThrows(CustomerNotFoundException.class, () -> customerController.joinCrib(1L, 1L));
    }

    @Test
    public void joinCribTest_CribNotFound()
            throws CustomerNotFoundException, CribNotFoundException, CribNameAlreadyTakenException {
        // Test when crib not found
        doThrow(new CribNotFoundException(1L)).when(cribService).addMember(1L, 1L);
        assertThrows(CribNotFoundException.class, () -> customerController.joinCrib(1L, 1L));
    }
}
