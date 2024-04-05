package com.cribhub.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ShoppingList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ShoppingListId;

    @ManyToOne
    @JoinColumn(name = "crib_id")
    @JsonIgnore
    private Crib crib;

    private String shoppingName;

    private String shoppingDescription;

    public ShoppingList(){

    }

    public ShoppingList(String shoppingDescription, String shoppingName){

        this.shoppingName = shoppingName;
        this.shoppingDescription = shoppingDescription;
    }
}
