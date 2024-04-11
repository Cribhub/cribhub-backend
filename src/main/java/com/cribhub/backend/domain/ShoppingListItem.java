package com.cribhub.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ShoppingListItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String name;

    private String description;

    @ManyToOne
    private Crib crib;

    @ManyToOne
    private Customer customer;

    public ShoppingListItem(){}

    public ShoppingListItem(String name, String description, Customer customer, Crib crib) {
        this.name = name;
        this.description = description;
        this.customer = customer;
        this.crib = crib;
    }
}
