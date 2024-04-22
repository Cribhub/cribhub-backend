package com.cribhub.backend.integration;

import com.cribhub.backend.domain.Crib;
import com.cribhub.backend.domain.Customer;
import com.cribhub.backend.dto.CustomerDTO;
import com.cribhub.backend.exceptions.CustomerNotFoundException;
import com.cribhub.backend.exceptions.EmailAlreadyInUseException;
import com.cribhub.backend.exceptions.CribNameAlreadyTakenException;
import com.cribhub.backend.services.intefaces.CribService;
import com.cribhub.backend.services.intefaces.CustomerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CribService cribService;

    private Customer testCustomer;
    private Crib testCrib;

    @BeforeEach
    public void setup() throws EmailAlreadyInUseException, CribNameAlreadyTakenException {
        testCustomer = new Customer();
        testCustomer.setUserName("TestCustomer");
        testCustomer.setEmail("test@testmail.com");
        testCustomer.setPassword(passwordEncoder.encode("password"));
        customerService.createCustomer(testCustomer);

        testCrib = new Crib();
        testCrib.setName("TestCrib");
        cribService.saveCrib(testCrib);
    }

    @AfterEach
    public void teardown() throws CustomerNotFoundException {
        customerService.deleteCustomer(testCustomer.getUserId());
        cribService.deleteCrib(testCrib.getCribId());
    }

    @Test
    public void testCreateCustomer() {
        Customer newCustomer = new Customer();
        newCustomer.setUserName("NewCustomer");
        newCustomer.setEmail("new@email.com");
        newCustomer.setPassword("password");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Customer> request = new HttpEntity<>(newCustomer, headers);

        ResponseEntity<CustomerDTO> response = restTemplate.exchange("/customer", HttpMethod.POST, request,
                CustomerDTO.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testGetCustomerById() {
        ResponseEntity<CustomerDTO> response = restTemplate.getForEntity("/customer/" + testCustomer.getUserId(),
                CustomerDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testJoinCrib() {
        ResponseEntity<Crib> response = restTemplate.postForEntity(
                "/customer/" + testCustomer.getUserId() + "/join/" + testCrib.getCribId(), null, Crib.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
