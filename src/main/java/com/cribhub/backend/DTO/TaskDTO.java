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
        dto.setTaskName(task.getTitle());
        dto.setDescription(task.getDescription());
        if (task.getCrib() != null) {
            dto.setCribId(task.getCrib().getCribId());
        }
        if (task.getCustomer() != null) {
            dto.setCustomerId(task.getCustomer().getUserId());
        }
        return dto;
    }
}
