package com.cribhub.backend.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;
import com.cribhub.backend.domain.Customer;
import com.cribhub.backend.domain.Task;

@Setter
@Getter
public class CustomerDTO {
    private Long userId;
    private String userName;
    private String email;
    private Long cribId;
    private List<Long> taskId;


    public static CustomerDTO ConvertToDTO(Customer customer) {
        var dto = new CustomerDTO();
        dto.setUserId(customer.getUserId());
        dto.setEmail(customer.getEmail());
        dto.setUserName(customer.getUserName());
        if (customer.getCrib() != null) {
            dto.cribId = customer.getCrib().getCribId();
        }
        if (customer.getTasks() != null){
            dto.setTaskId(customer.getTasks().stream()
                    .map(Task::getTaskId)
                    .collect(Collectors.toList()));}
        return dto;
    }
}