package com.cribhub.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Crib {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cribId;

    @NotBlank
    private String name;

    @OneToMany(mappedBy = "crib")
    @JsonIgnore
    private List<Customer> cribMembers;

    @OneToMany(mappedBy = "crib")
    @JsonIgnore
    private List<ShoppingListItem> shoppingList;

    @OneToMany(mappedBy = "crib")
    @JsonIgnore
    private List<Task> tasks;

    public Crib() {
        this.cribMembers = new ArrayList<>();
    }

    public Crib(String name, List<Customer> cribMembers, List<ShoppingListItem> shoppingList, List<Task> tasks) {
        this.name = name;
        this.cribMembers = cribMembers;
        this.shoppingList = shoppingList;
        this.tasks = tasks;
    }

}
