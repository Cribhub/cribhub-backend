package com.cribhub.backend.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskUpdateDTO {
    private Long cribId;

    private String taskName;

    private String description;

    private Long customerId;

    public TaskUpdateDTO(Long cribId, String taskName, String description, Long customerId) {
        this.cribId = cribId;
        this.taskName = taskName;
        this.description = description;
        this.customerId = customerId;
    }
}
