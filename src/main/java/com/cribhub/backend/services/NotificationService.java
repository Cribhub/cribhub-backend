package com.cribhub.backend.services;

import com.cribhub.backend.domain.Customer;
import com.cribhub.backend.domain.Notification;
import com.cribhub.backend.repositories.CustomerRepository;
import com.cribhub.backend.repositories.NotificationRepostitory;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class NotificationService {

    private final NotificationRepostitory notificationRepostitory;
    private final CustomerRepository customerRepository;

    @Autowired
    public NotificationService(NotificationRepostitory notificationRepostitory, CustomerRepository customerRepository) {
        this.notificationRepostitory = notificationRepostitory;
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Optional<Notification> createNotificationForCustomer(Long customerId, Notification notification) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isEmpty()) {
            return Optional.empty();
        }

        Customer customer = customerOptional.get();
        notification.setCustomer(customer);
        Notification savedNotification = notificationRepostitory.save(notification);
        return Optional.of(savedNotification);
    }

    public Optional<Notification> getNotificationById(Long id) {
        return notificationRepostitory.findById(id);
    }

    public Notification createOrUpdateNotification(Notification notification) {
        return notificationRepostitory.save(notification);
    }

    public void deleteNotification(Long id) {
        notificationRepostitory.deleteById(id);
    }
}
