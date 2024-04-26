package com.cribhub.backend.dto;

import com.cribhub.backend.domain.Notification;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NotificationDTO {
    private Long id;
    private String name;
    private String description;
    private Boolean isRead;
    private Long customerId;

    public static NotificationDTO ConvertToDTO(Notification notification) {
        var dto = new NotificationDTO();
        dto.setId(notification.getId());
        dto.setName(notification.getName());
        dto.setDescription(notification.getDescription());
        dto.setIsRead(notification.getIsRead());

        if (notification.getCustomer() != null) {
            dto.customerId = notification.getCustomer().getUserId();
        }

        return dto;
    }
}
