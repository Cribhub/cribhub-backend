package com.cribhub.backend.services.intefaces;

import com.cribhub.backend.controllers.exceptions.CustomerNotFoundException;
import com.cribhub.backend.controllers.exceptions.EmailAlreadyInUseException;
import com.cribhub.backend.controllers.exceptions.UsernameAlreadyTakenException;
import com.cribhub.backend.domain.Customer;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService {
    Customer getCustomerById(Long id) throws CustomerNotFoundException;
    Customer createCustomer(Customer customer) throws EmailAlreadyInUseException, UsernameAlreadyTakenException;
    Customer updateCustomer(Long id, Customer customer) throws CustomerNotFoundException;

    void deleteCustomer(Long id) throws CustomerNotFoundException;
}