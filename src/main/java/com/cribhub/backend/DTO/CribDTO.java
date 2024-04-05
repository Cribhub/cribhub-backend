package com.cribhub.backend.DTO;

import com.cribhub.backend.domain.ShoppingList;
import com.cribhub.backend.domain.Crib;
import com.cribhub.backend.domain.Task;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class CribDTO {
    private Long cribId;
    private String cribName;
    private List<CribMemberDTO> cribMembers; // Changed to list of CribMemberDTO
    private List<Long> taskId;
    private List<ShoppingList> shoppingListItems;

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
        dto.setCribName(crib.getCribName());
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
        if (crib.getShoppingListItems() != null) {
            dto.setShoppingListItems(crib.getShoppingListItems());
        }
        if (crib.getTasks() != null) {
            dto.setTaskId(crib.getTasks().stream()
                    .map(Task::getTaskId)
                    .collect(Collectors.toList()));
        }
        return dto;
    }
}