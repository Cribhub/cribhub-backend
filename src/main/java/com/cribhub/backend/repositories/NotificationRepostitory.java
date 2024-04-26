package com.cribhub.backend.repositories;

import com.cribhub.backend.domain.Notification;
import org.springframework.data.repository.CrudRepository;

public interface NotificationRepostitory extends CrudRepository<Notification, Long> {
}
