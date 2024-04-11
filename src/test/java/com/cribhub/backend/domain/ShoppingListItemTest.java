package com.cribhub.backend.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ShoppingListItemTest {

    private ShoppingListItem shoppingListItem;

    @BeforeEach
    public void setUp() {
        shoppingListItem = new ShoppingListItem();
    }

    @Test
    public void testShoppingListId() {
        Long id = 1L;
        shoppingListItem.setId(id);
        assertEquals(id, shoppingListItem.getId());
    }

    @Test
    public void testCrib() {
        Crib crib = new Crib();
        shoppingListItem.setCrib(crib);
        assertEquals(crib, shoppingListItem.getCrib());
    }

    @Test
    public void testShoppingName() {
        String name = "Test Shopping List";
        shoppingListItem.setName(name);
        assertEquals(name, shoppingListItem.getName());
    }

    @Test
    public void testShoppingDescription() {
        String description = "This is a test shopping list";
        shoppingListItem.setDescription(description);
        assertEquals(description, shoppingListItem.getDescription());
    }

    @Test
    public void testConstructor() {
        String name = "Test Shopping List";
        String description = "This is a test shopping list";
        ShoppingListItem shoppingListItem = new ShoppingListItem(name, description, null, null);
        assertNotNull(shoppingListItem);
        assertEquals(name, shoppingListItem.getName());
        assertEquals(description, shoppingListItem.getDescription());
    }
}