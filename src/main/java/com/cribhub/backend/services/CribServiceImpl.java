package com.cribhub.backend.services;

import com.cribhub.backend.domain.Crib;
import com.cribhub.backend.domain.Customer;
import com.cribhub.backend.exceptions.CribNameAlreadyTakenException;
import com.cribhub.backend.exceptions.CribNotFoundException;
import com.cribhub.backend.exceptions.CustomerNotFoundException;
import com.cribhub.backend.repositories.CribRepository;
import com.cribhub.backend.repositories.CustomerRepository;
import com.cribhub.backend.services.intefaces.CribService;

import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class CribServiceImpl implements CribService {
    private final CribRepository cribRepository;
    private final CustomerRepository customerRepository;

    public CribServiceImpl(CribRepository cribRepository, CustomerRepository customerRepository) {
        this.cribRepository = cribRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Crib saveCrib(Crib crib) throws CribNameAlreadyTakenException {
        if (cribRepository.findByName(crib.getName()).isPresent()) {
            throw new CribNameAlreadyTakenException(crib.getName());
        }

        return cribRepository.save(crib);
    }

    @Override
    public Crib getCribById(Long cribId) throws CribNotFoundException {
        Crib crib = cribRepository.findById(cribId).orElse(null);
        if (crib == null) {
            throw new CribNotFoundException(cribId);
        }

        return cribRepository.findById(cribId).orElse(null);
    }

    @Override
    public List<Crib> getAllCribs() {
        return (List<Crib>) cribRepository.findAll();
    }

    @Override
    public void deleteCrib(Long cribId) {
        cribRepository.deleteById(cribId);
    }

    @Override
    public Crib updateCrib(Crib crib) throws CribNameAlreadyTakenException, CribNotFoundException {
        Crib existingCrib = getCribById(crib.getCribId());

        if (cribRepository.findByName(crib.getName()).isPresent()) {
            throw new CribNameAlreadyTakenException(crib.getName());
        }

        existingCrib.setName(crib.getName());
        cribRepository.save(existingCrib);

        return crib;
    }

    @Override
    public void addMember(Long cribId, Long customerId) throws CribNotFoundException, CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        Crib crib = cribRepository.findById(cribId).orElse(null);

        if (customer == null) {
            log.error("Could not find customer with id {} when trying to join crib with id {}", customerId, cribId);
            throw new CustomerNotFoundException(customerId);
        }

        if (crib == null) {
            log.error("Could not find crib with id {} when trying to add customer with id {}", cribId, customerId);
            throw new CribNotFoundException(cribId);
        }

        customer.setCrib(crib);
        crib.getCribMembers().add(customer);

        customerRepository.save(customer);
        cribRepository.save(crib);
    }

    @Override
    public void removeMember(Long cribId, Long customerId) throws CribNotFoundException, CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        Crib crib = cribRepository.findById(cribId).orElse(null);

        if (customer == null) {
            log.error("Could not find customer with id {} when trying to join crib with id {}", customerId, cribId);
            throw new CustomerNotFoundException(customerId);
        }

        if (crib == null) {
            log.error("Could not find crib with id {} when trying to add customer with id {}", cribId, customerId);
            throw new CribNotFoundException(cribId);
        }

        customer.setCrib(null);
        crib.getCribMembers().remove(customer);

        customerRepository.save(customer);
        cribRepository.save(crib);
    }

    @Override
    public List<Customer> getMembers(Long cribId) throws CribNotFoundException {
        Crib crib = cribRepository.findById(cribId).orElse(null);

        if (crib == null) {
            log.error("Could not find crib with id {} when trying to get it's members", cribId);
            throw new CribNotFoundException(cribId);
        }

        return crib.getCribMembers();
    }
}
