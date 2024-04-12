package com.cribhub.backend.controllers;

import com.cribhub.backend.domain.Crib;
import com.cribhub.backend.domain.Customer;
import com.cribhub.backend.dto.CustomerDTO;
import com.cribhub.backend.dto.CustomerUpdateDTO;
import com.cribhub.backend.exceptions.CribNotFoundException;
import com.cribhub.backend.exceptions.CustomerNotFoundException;
import com.cribhub.backend.exceptions.EmailAlreadyInUseException;
import com.cribhub.backend.exceptions.UsernameAlreadyTakenException;
import com.cribhub.backend.services.CustomerServiceImpl;
import com.cribhub.backend.services.intefaces.CribService;
import com.cribhub.backend.services.intefaces.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@CrossOrigin
@Log4j2
public class CustomerController {

    private final CustomerService customerService;
    private final CribService cribService;

    public CustomerController(CustomerServiceImpl customerService, CribService cribService) {
        this.customerService = customerService;
        this.cribService = cribService;
    }

    @PostMapping("/customer")
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody @Valid Customer customer) throws EmailAlreadyInUseException, UsernameAlreadyTakenException {
        customerService.createCustomer(customer);

        log.info("Customer created: id-{} name-{} email-{}", customer.getUserId(), customer.getUserName(), customer.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(CustomerDTO.ConvertToDTO(customer));
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable @Min(1) long id) throws CustomerNotFoundException {
        Customer customer = customerService.getCustomerById(id);

        log.info("Customer retrieved: id-{} name-{} email-{}", customer.getUserId(), customer.getUserName(), customer.getEmail());
        return ResponseEntity.ok(CustomerDTO.ConvertToDTO(customer));
    }

    @PutMapping("/customer/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable long id, @RequestBody @Valid CustomerUpdateDTO updateDto) throws CustomerNotFoundException {
        Customer updatedCustomer = customerService.updateCustomer(id, CustomerDTO.toCustomer(updateDto));

        log.info("Customer updated: id-{} name-{} email-{}", updatedCustomer.getUserId(), updatedCustomer.getUserName(), updatedCustomer.getEmail());
        return ResponseEntity.ok(CustomerDTO.ConvertToDTO(updatedCustomer));
    }

    @DeleteMapping("customer/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable long id) throws CustomerNotFoundException {
        customerService.deleteCustomer(id);

        log.warn("Customer with id {} deleted", id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("customer/{customerId}/join/{cribId}")
    public ResponseEntity<Crib> joinCrib(@PathVariable Long cribId, @PathVariable Long customerId) throws CustomerNotFoundException, CribNotFoundException {
        Crib crib = cribService.getCribById(cribId);
        if (crib == null) {
            log.error("Could not join crib with id {} because it does not exist", cribId);
            return ResponseEntity.notFound().build();
        }

        Customer customer = customerService.getCustomerById(customerId);
        if (customer == null) {
            log.error("Could not join crib with cribId {} because customer with customerId {} does not exist", cribId, customerId);
            return ResponseEntity.notFound().build();
        }
        customer.setCrib(crib);
        crib.getCribMembers().add(customer);
        Crib updatedCrib = cribService.saveCrib(crib);

        log.info("Customer with id {} joined crib with id {}", customerId, cribId);
        return ResponseEntity.ok(updatedCrib);
    }


}