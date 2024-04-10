package com.cribhub.backend.exceptions;

public class CustomerNotFoundException extends Throwable {
    public CustomerNotFoundException(long customerId) {
        super("Customer with id " + customerId + " not found");
    }
}
