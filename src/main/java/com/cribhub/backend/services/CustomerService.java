package com.cribhub.backend.services;

import com.cribhub.backend.domain.Customer;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService {
    Customer getCustomerById(Long id);
    Customer createCustomer(Customer customer);
    Customer updateCustomer(Long id, Customer customer);
    void deleteCustomer(Long id);
}