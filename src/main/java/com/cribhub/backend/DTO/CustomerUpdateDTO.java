package com.cribhub.backend.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerUpdateDTO {
    private String userName;

    @Email
    private String email;

    @Size(min = 8)
    private String password;

    public CustomerUpdateDTO(String name, String mail, String password) {
        this.userName = name;
        this.email = mail;
        this.password = password;
    }

}