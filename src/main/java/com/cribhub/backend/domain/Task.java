package com.cribhub.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long taskId;

    @NotBlank
    private String title;

    private String description;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Crib crib;

    public Task(){}

    public Task(String taskName, String description, Customer customer, Crib crib){
        this.title = taskName;
        this.description = description;
        this.customer = customer;
        this.crib = crib;
    }
}
