package com.cribhub.backend.services;

import com.cribhub.backend.controllers.exceptions.CustomerNotFoundException;
import com.cribhub.backend.controllers.exceptions.EmailAlreadyInUseException;
import com.cribhub.backend.controllers.exceptions.UsernameAlreadyTakenException;
import com.cribhub.backend.domain.Customer;
import com.cribhub.backend.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Customer getCustomerById(Long id) throws CustomerNotFoundException {
        //Check if user exists
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()) {
            throw new CustomerNotFoundException(id);
        }

        return customer.get();
    }

    @Override
    public Customer createCustomer(Customer customer) throws EmailAlreadyInUseException, UsernameAlreadyTakenException {
        //Check if user already exists
        String email = customer.getEmail();
        String userName = customer.getUserName();

        if (customerRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyInUseException(email);
        }

        if (customerRepository.findByUserName(userName).isPresent()) {
            throw new UsernameAlreadyTakenException(userName);
        }

        //Hash the password before saving
        String hashedPassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(hashedPassword);

        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) throws CustomerNotFoundException {
        //Check if the user exists
        Optional<Customer> existingCustomer = customerRepository.findById(id);
        if (existingCustomer.isEmpty()) {
            throw new CustomerNotFoundException(id);
        }

        Customer customerToUpdate = existingCustomer.get();

        if (customer.getEmail() != null) {
            customerToUpdate.setEmail(customer.getEmail());
        }

        if (customer.getUserName() != null) {
            customerToUpdate.setUserName(customer.getUserName());
        }

        if (customer.getPassword() != null) {
            customerToUpdate.setPassword(passwordEncoder.encode(customer.getPassword()));
        }

        return customerRepository.save(customerToUpdate);
    }

    @Override
    public void deleteCustomer(Long id) throws CustomerNotFoundException {
        //Check if the user exists
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()) {
            throw new CustomerNotFoundException(id);
        }

        customerRepository.deleteById(id);
    }
}