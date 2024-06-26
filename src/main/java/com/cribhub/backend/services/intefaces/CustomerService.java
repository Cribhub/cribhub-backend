package com.cribhub.backend.services.intefaces;

import com.cribhub.backend.domain.Customer;
import com.cribhub.backend.exceptions.CustomerNotFoundException;
import com.cribhub.backend.exceptions.EmailAlreadyInUseException;
import com.cribhub.backend.exceptions.UsernameAlreadyTakenException;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService {
    Customer getCustomerById(Long id) throws CustomerNotFoundException;

    Customer createCustomer(Customer customer) throws EmailAlreadyInUseException, UsernameAlreadyTakenException;

    Customer updateCustomer(Long id, Customer customer) throws CustomerNotFoundException;

    void deleteCustomer(Long id) throws CustomerNotFoundException;

    void updateAllNotificationsForCustomer(long customerId) throws CustomerNotFoundException;
}
