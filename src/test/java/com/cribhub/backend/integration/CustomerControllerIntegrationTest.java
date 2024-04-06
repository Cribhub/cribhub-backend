package com.cribhub.backend.integration;

import com.cribhub.backend.DTO.CustomerDTO;
import com.cribhub.backend.domain.Crib;
import com.cribhub.backend.domain.Customer;
import com.cribhub.backend.services.CribService;
import com.cribhub.backend.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public void setup() {
        testCustomer = new Customer();
        testCustomer.setPassword(passwordEncoder.encode("password"));
        customerService.createCustomer(testCustomer);

        testCrib = new Crib();
        cribService.saveCrib(testCrib);
    }

    @Test
    public void testCreateCustomer() {
        Customer newCustomer = new Customer();
        newCustomer.setPassword("password");

        ResponseEntity<CustomerDTO> response = restTemplate.postForEntity("/customer", newCustomer, CustomerDTO.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testGetCustomerById() {
        ResponseEntity<CustomerDTO> response = restTemplate.getForEntity("/customer/" + testCustomer.getUserId(), CustomerDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testJoinCrib() {
        ResponseEntity<Crib> response = restTemplate.postForEntity("/customer/" + testCustomer.getUserId() + "/join/" + testCrib.getCribId(), null, Crib.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
