package com.cribhub.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCribDTO {
  @NotBlank
  private String cribName;

  public CreateCribDTO() {

  }

  public CreateCribDTO(String cribName) {
    this.cribName = cribName;
  }
}
