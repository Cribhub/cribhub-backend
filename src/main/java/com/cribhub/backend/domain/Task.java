package com.cribhub.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

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

    private Boolean completed;

    private LocalDate deadlineDate;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Crib crib;

    public Task(){}

    public Task(String taskName, String description, Boolean completed, LocalDate deadlineDate, Customer customer, Crib crib){
        this.title = taskName;
        this.description = description;
        this.completed = completed;
        this.deadlineDate = deadlineDate;
        this.customer = customer;
        this.crib = crib;
    }
}
