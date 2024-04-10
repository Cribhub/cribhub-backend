package com.cribhub.backend.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CribTest {

    private Crib crib;

    @BeforeEach
    public void setUp() {
        crib = new Crib();
    }

    @Test
    public void testCribId() {
        Long id = 1L;
        crib.setCribId(id);
        assertEquals(id, crib.getCribId());
    }

    @Test
    public void testCribName() {
        String name = "Test Crib";
        crib.setName(name);
        assertEquals(name, crib.getName());
    }

    @Test
    public void testCribMembers() {
        List<Customer> members = new ArrayList<>();
        crib.setCribMembers(members);
        assertEquals(members, crib.getCribMembers());
    }

    @Test
    public void testShoppingListItems() {
        List<ShoppingListItem> items = new ArrayList<>();
        crib.setShoppingList(items);
        assertEquals(items, crib.getShoppingList());
    }

    @Test
    public void testTasks() {
        List<Task> tasks = new ArrayList<>();
        crib.setTasks(tasks);
        assertEquals(tasks, crib.getTasks());
    }

    @Test
    public void testConstructor() {
        List<Customer> members = new ArrayList<>();
        List<ShoppingListItem> items = new ArrayList<>();
        List<Task> tasks = new ArrayList<>();
        String name = "Test Crib";
        Crib crib = new Crib(name, members, items, tasks);
        assertNotNull(crib);
        assertEquals(name, crib.getName());
        assertEquals(members, crib.getCribMembers());
        assertEquals(items, crib.getShoppingList());
        assertEquals(tasks, crib.getTasks());
    }
}