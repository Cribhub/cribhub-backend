package com.cribhub.backend.controllers;

import com.cribhub.backend.DTO.CustomerDTO;
import com.cribhub.backend.domain.Crib;
import com.cribhub.backend.domain.Customer;
import com.cribhub.backend.services.CribService;
import com.cribhub.backend.services.CustomerService;
import com.cribhub.backend.services.CustomerServiceImpl;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@CrossOrigin
public class CustomerController {

    private final CustomerService customerService;
    private final PasswordEncoder passwordEncoder;
    private final CribService cribService;


    public CustomerController(CustomerServiceImpl customerService, CribService cribService, PasswordEncoder passwordEncoder) {
        this.customerService = customerService;
        this.cribService = cribService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/customer")
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody Customer customer) {
        String hashedPassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(hashedPassword);
        customerService.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(CustomerDTO.ConvertToDTO(customer));
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable long id) {
        Customer customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(CustomerDTO.ConvertToDTO(customer));
    }

    @DeleteMapping("customer/{id}")
    public ResponseEntity<String> deleteCustomer( @PathVariable long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("customer/{customerId}/join/{cribId}")
    public ResponseEntity<Crib> joinCrib(@PathVariable Long cribId, @PathVariable Long customerId) {
        Crib crib = cribService.getCribById(cribId);
        if (crib == null) {
            return ResponseEntity.notFound().build();
        }

        Customer customer = customerService.getCustomerById(customerId);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        customer.setCrib(crib);
        crib.getCribMembers().add(customer);
        Crib updatedCrib = cribService.saveCrib(crib);

        return ResponseEntity.ok(updatedCrib);
    }


}