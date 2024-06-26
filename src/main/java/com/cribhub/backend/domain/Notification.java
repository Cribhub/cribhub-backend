package com.cribhub.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String name;

    private String description;

    private Boolean isRead = false;

    @ManyToOne
    private Customer customer;

    public Notification(){}

    public Notification(String name, String description, Customer customer, Boolean isRead) {
        this.name = name;
        this.description = description;
        this.customer = customer;
        this.isRead = isRead;
    }
}



