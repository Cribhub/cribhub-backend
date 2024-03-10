package com.cribhub.backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "todos")
public class TodoItem {
    @Id
    private Long id;

    private String title;

    private boolean completed;
}
