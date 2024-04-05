package com.cribhub.backend.repositories;

import com.cribhub.backend.domain.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Customer findByEmail(String email);


    Customer findByUserName(String username);
}
