package com.cribhub.backend.dto;

import com.cribhub.backend.domain.Crib;
import com.cribhub.backend.domain.ShoppingListItem;
import com.cribhub.backend.domain.Task;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class CribDTO {
    private Long cribId;
    private String cribName;
    private List<CribMemberDTO> cribMembers; // Changed to list of CribMemberDTO
    private List<Task> taskList;
    private List<ShoppingListItem> shoppingListItemItems;

    // Nested DTO class for crib members
    @Setter
    @Getter
    public static class CribMemberDTO {
        private Long id;
        private String name;
    }

    public static CribDTO ConvertToCribDTO(Crib crib) {
        var dto = new CribDTO();
        dto.setCribId(crib.getCribId());
        dto.setCribName(crib.getName());
        if (crib.getCribMembers() != null) {
            dto.setCribMembers(crib.getCribMembers().stream()
                    .map(customer -> {
                        CribMemberDTO memberDTO = new CribMemberDTO();
                        memberDTO.setId(customer.getUserId());
                        memberDTO.setName(customer.getUserName());
                        return memberDTO;
                    })
                    .collect(Collectors.toList()));
        }
        if (crib.getShoppingList() != null) {
            dto.setShoppingListItemItems(crib.getShoppingList());
        }
        if (crib.getTasks() != null) {
            dto.setTaskList(new ArrayList<>(crib.getTasks()));
        }
        return dto;
    }
}