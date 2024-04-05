package com.cribhub.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long taskId;

    @ManyToOne
    @JoinColumn(name = "crib_id")
    @JsonIgnore
    private Crib crib;

    private String taskName;

    private String description;

    @ManyToOne
    private Customer customerTask;


    public Task(){
    }

    public Task(Crib crib, String taskName, String description, Customer customerTask){
        this.crib = crib;
        this.taskName = taskName;
        this.description = description;
        this.customerTask = customerTask;
    }

}
