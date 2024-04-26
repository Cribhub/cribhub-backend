package com.cribhub.backend.controllers;

import com.cribhub.backend.domain.Notification;
import com.cribhub.backend.services.NotificationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/notification")
@CrossOrigin
@Log4j2
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long id) {
        Optional<Notification> notificationOptional = notificationService.getNotificationById(id);

        // If the notification is not found, return a 404 Not Found response
        if (notificationOptional.isEmpty()) {
            log.error("Notification with id {} not found", id);
            return ResponseEntity.notFound().build();
        }

        log.info("Notification retrieved: id-{} message-{}", id, notificationOptional.get().getName());
        return ResponseEntity.ok(notificationOptional.get());
    }

    @PostMapping("customer/{customerId}")
    public ResponseEntity<Notification> createNotificationForCustomer(@PathVariable Long customerId,
            @RequestBody Notification notification) {
        Optional<Notification> savedNotification = notificationService.createNotificationForCustomer(customerId,
                notification);

        // If the notification is not saved, return a 500 internal server error
        if (savedNotification.isEmpty()) {
            log.error(
                    "Unable to create notification. Verify that the customer with id {} exists and that the notification is valid",
                    customerId);
            return ResponseEntity.internalServerError().build();
        }

        log.info("Notification created: id-{} message-{}", savedNotification.get().getId(),
                savedNotification.get().getName());
        return ResponseEntity.ok(savedNotification.get());
    }
}
