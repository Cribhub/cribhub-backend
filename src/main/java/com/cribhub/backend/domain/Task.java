package com.cribhub.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

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

    @Future
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
