package com.cribhub.backend.dto;

import com.cribhub.backend.domain.ShoppingListItem;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ShoppingListItemDTO {
    private Long id;
    private String name;
    private String description;
    private Long cribId;

    public static ShoppingListItemDTO ConvertToDTO(ShoppingListItem shoppingListItem) {
        var dto = new ShoppingListItemDTO();
        dto.setId(shoppingListItem.getId());
        dto.setName(shoppingListItem.getName());
        dto.setDescription(shoppingListItem.getDescription());

        if (shoppingListItem.getCrib() != null) {
            dto.cribId = shoppingListItem.getCrib().getCribId();
        }

        return dto;
    }
}
