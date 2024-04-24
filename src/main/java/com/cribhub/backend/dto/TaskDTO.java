package com.cribhub.backend.dto;

import com.cribhub.backend.domain.Task;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class TaskDTO {
    private Long taskId;

    private Long cribId;

    private String taskName;

    private String description;

    private LocalDate deadlineDate;

    private Boolean completed;

    private Long customerId;

    public static TaskDTO TaskUpdateDTO (Task task) {
        var dto = new TaskDTO();
        dto.setTaskId(task.getTaskId());
        dto.setTaskName(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setCompleted(task.getCompleted());
        dto.setDeadlineDate(task.getDeadlineDate());
        if (task.getCrib() != null) {
            dto.setCribId(task.getCrib().getCribId());
        }
        if (task.getCustomer() != null) {
            dto.setCustomerId(task.getCustomer().getUserId());
        }
        return dto;
    }
}
