package com.cribhub.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
// testd

@Entity
@Getter
@Setter
public class Crib {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cribId;

    private String cribName;

    @OneToMany(mappedBy = "crib")
    private List<Customer> cribMembers;

    @OneToMany(mappedBy = "crib")
    private List <ShoppingList> shoppingListItems;

    @OneToMany(mappedBy = "crib")
    private List <Task> tasks;


    public Crib() {
        this.cribMembers = new ArrayList<>();
    }

    public Crib(String cribName, List<Customer> cribMembers, List<ShoppingList> shoppingListItems, List<Task> tasks){
        this.cribName = cribName;
        this.cribMembers = cribMembers;
        this.shoppingListItems = shoppingListItems;
        this.tasks = tasks;
    }



}

