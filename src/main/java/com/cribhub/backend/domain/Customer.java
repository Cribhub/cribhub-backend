package com.cribhub.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @NotBlank
    private String userName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password;

    @ManyToOne
    private Crib crib;

    @OneToMany
    private List<Task> tasks;

    public Customer() {
    }

    public Customer(String userName, String email, String password, Crib crib) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.crib = crib;
        this.tasks = new ArrayList<>();
    }

    public void setCrib(Crib crib) {
        this.crib = crib;
        if (crib != null && !crib.getCribMembers().contains(this))
            crib.getCribMembers().add(this);
    }
}