package com.cribhub.backend.dto;

import com.cribhub.backend.domain.Crib;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CribDTO {
    private Long cribId;
    private String cribName;


    public static CribDTO ConvertToCribDTO(Crib crib) {
        var dto = new CribDTO();
        dto.setCribId(crib.getCribId());
        dto.setCribName(crib.getName());
        return dto;
    }
}