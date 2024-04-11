package com.cribhub.backend.dto;

import com.cribhub.backend.domain.Customer;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CribUpdateDTO {
    private String cribName;

    private List<Customer> cribMemberIds;

    public CribUpdateDTO(String cribName, List<Customer> cribMemberIds) {
        this.cribName = cribName;
        this.cribMemberIds = cribMemberIds;
    }
}