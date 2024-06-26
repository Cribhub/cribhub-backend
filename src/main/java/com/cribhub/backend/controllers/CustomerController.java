package com.cribhub.backend.controllers;

import com.cribhub.backend.domain.Crib;
import com.cribhub.backend.domain.Customer;
import com.cribhub.backend.domain.Notification;
import com.cribhub.backend.domain.ShoppingListItem;
import com.cribhub.backend.dto.CustomerDTO;
import com.cribhub.backend.dto.CustomerUpdateDTO;
import com.cribhub.backend.dto.NotificationDTO;
import com.cribhub.backend.dto.ShoppingListItemDTO;
import com.cribhub.backend.exceptions.CribNotFoundException;
import com.cribhub.backend.exceptions.CustomerNotFoundException;
import com.cribhub.backend.exceptions.EmailAlreadyInUseException;
import com.cribhub.backend.exceptions.UsernameAlreadyTakenException;
import com.cribhub.backend.exceptions.CribNameAlreadyTakenException;
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

import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody @Valid Customer customer)
            throws EmailAlreadyInUseException, UsernameAlreadyTakenException {
        customerService.createCustomer(customer);

        log.info("Customer created: id-{} name-{} email-{}", customer.getUserId(), customer.getUserName(),
                customer.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(CustomerDTO.ConvertToDTO(customer));
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable @Min(1) long id) throws CustomerNotFoundException {
        Customer customer = customerService.getCustomerById(id);

        log.info("Customer retrieved: id-{} name-{} email-{}", customer.getUserId(), customer.getUserName(),
                customer.getEmail());
        return ResponseEntity.ok(CustomerDTO.ConvertToDTO(customer));
    }

    @PutMapping("/customer/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable long id,
            @RequestBody @Valid CustomerUpdateDTO updateDto) throws CustomerNotFoundException {
        Customer updatedCustomer = customerService.updateCustomer(id, CustomerDTO.toCustomer(updateDto));

        log.info("Customer updated: id-{} name-{} email-{}", updatedCustomer.getUserId(), updatedCustomer.getUserName(),
                updatedCustomer.getEmail());
        return ResponseEntity.ok(CustomerDTO.ConvertToDTO(updatedCustomer));
    }

    @DeleteMapping("customer/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable long id) throws CustomerNotFoundException {
        customerService.deleteCustomer(id);

        log.warn("Customer with id {} deleted", id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("customer/{customerId}/join/{cribId}")
    public ResponseEntity<Void> joinCrib(@PathVariable Long cribId, @PathVariable Long customerId)
            throws CustomerNotFoundException, CribNotFoundException, CribNameAlreadyTakenException {

        cribService.addMember(cribId, customerId);

        log.info("Customer with id {} joined crib with id {}", customerId, cribId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("customer/{customerId}/notifications")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByCustomerId(@PathVariable long customerId) throws CustomerNotFoundException {
        Customer customer = customerService.getCustomerById(customerId);

        List<Notification> notifications = customer.getNotifications();
        List<NotificationDTO> notificationDTOs = notifications.stream()
                .map(NotificationDTO::ConvertToDTO)
                .collect(Collectors.toList());

        log.info("Notifications for customer with id {} retrieved", customerId);
        return ResponseEntity.ok(notificationDTOs);
    }

    @PutMapping("customer/{customerId}/notifications")
    public ResponseEntity<Void> updateAllNotificationsForCustomer(@PathVariable long customerId) throws CustomerNotFoundException {
        customerService.updateAllNotificationsForCustomer(customerId);

        log.info("All notifications for customer with id {} updated", customerId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("customer/{customerId}/leave/{cribId}")
    public ResponseEntity<Void> leaveCrib(@PathVariable Long cribId, @PathVariable Long customerId)
            throws CustomerNotFoundException, CribNotFoundException, CribNameAlreadyTakenException {

        cribService.removeMember(cribId, customerId);

        log.info("Customer with id {} left crib with id {}", customerId, cribId);
        return ResponseEntity.ok().build();
    }

}
