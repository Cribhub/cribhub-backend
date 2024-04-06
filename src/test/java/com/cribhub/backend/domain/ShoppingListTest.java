package com.cribhub.backend.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ShoppingListTest {

    private ShoppingList shoppingList;

    @BeforeEach
    public void setUp() {
        shoppingList = new ShoppingList();
    }

    @Test
    public void testShoppingListId() {
        Long id = 1L;
        shoppingList.setShoppingListId(id);
        assertEquals(id, shoppingList.getShoppingListId());
    }

    @Test
    public void testCrib() {
        Crib crib = new Crib();
        shoppingList.setCrib(crib);
        assertEquals(crib, shoppingList.getCrib());
    }

    @Test
    public void testShoppingName() {
        String name = "Test Shopping List";
        shoppingList.setShoppingName(name);
        assertEquals(name, shoppingList.getShoppingName());
    }

    @Test
    public void testShoppingDescription() {
        String description = "This is a test shopping list";
        shoppingList.setShoppingDescription(description);
        assertEquals(description, shoppingList.getShoppingDescription());
    }

    @Test
    public void testConstructor() {
        String name = "Test Shopping List";
        String description = "This is a test shopping list";
        ShoppingList shoppingList = new ShoppingList(description, name);
        assertNotNull(shoppingList);
        assertEquals(name, shoppingList.getShoppingName());
        assertEquals(description, shoppingList.getShoppingDescription());
    }
}