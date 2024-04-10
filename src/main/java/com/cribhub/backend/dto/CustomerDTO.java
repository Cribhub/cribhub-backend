package com.cribhub.backend.dto;

import com.cribhub.backend.domain.Customer;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerDTO {
    private Long userId;
    private String userName;
    private String email;
    private Long cribId;

    public static CustomerDTO ConvertToDTO(Customer customer) {
        var dto = new CustomerDTO();
        dto.setUserId(customer.getUserId());
        dto.setEmail(customer.getEmail());
        dto.setUserName(customer.getUserName());

        if (customer.getCrib() != null) {
            dto.cribId = customer.getCrib().getCribId();
        }

        return dto;
    }

    public static Customer toCustomer(CustomerUpdateDTO dto) {
        var customer = new Customer();
        customer.setEmail(dto.getEmail());
        customer.setUserName(dto.getUserName());
        customer.setPassword(dto.getPassword());
        return customer;
    }
}