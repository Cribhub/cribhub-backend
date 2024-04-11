package com.cribhub.backend.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TaskTest {

    private Task task;

    @BeforeEach
    public void setUp() {
        task = new Task();
    }

    @Test
    public void testTaskId() {
        Long id = 1L;
        task.setTaskId(id);
        assertEquals(id, task.getTaskId());
    }

    @Test
    public void testCrib() {
        Crib crib = new Crib();
        task.setCrib(crib);
        assertEquals(crib, task.getCrib());
    }

    @Test
    public void testTaskName() {
        String name = "Test Task";
        task.setTitle(name);
        assertEquals(name, task.getTitle());
    }

    @Test
    public void testDescription() {
        String description = "This is a test task";
        task.setDescription(description);
        assertEquals(description, task.getDescription());
    }

    @Test
    public void testCustomerTask() {
        Customer customer = new Customer();
        task.setCustomer(customer);
        assertEquals(customer, task.getCustomer());
    }

    @Test
    public void testConstructor() {
        Crib crib = new Crib();
        String name = "Test Task";
        String description = "This is a test task";
        Customer customer = new Customer();
        Task task = new Task(name, description, customer, crib);
        assertNotNull(task);
        assertEquals(crib, task.getCrib());
        assertEquals(name, task.getTitle());
        assertEquals(description, task.getDescription());
    }
}