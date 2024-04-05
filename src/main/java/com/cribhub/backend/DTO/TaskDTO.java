package com.cribhub.backend.DTO;

import lombok.Getter;
import lombok.Setter;

import com.cribhub.backend.domain.Task;

@Setter
@Getter
public class TaskDTO {
    private Long taskId;

    private Long cribId;

    private String taskName;

    private String description;

    private Long customerId;

    public static TaskDTO TaskUpdateDTO (Task task) {
        var dto = new TaskDTO();
        dto.setTaskId(task.getTaskId());
        dto.setTaskName(task.getTaskName());
        dto.setDescription(task.getDescription());
        if (task.getCrib() != null) {
            dto.setCribId(task.getCrib().getCribId());
        }
        if (task.getCustomerTask() != null) {
            dto.setCustomerId(task.getCustomerTask().getUserId());
        }
        return dto;
    }

    }
