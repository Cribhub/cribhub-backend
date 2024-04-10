package com.cribhub.backend.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CustomerTest {

    private Customer customer;

    @BeforeEach
    public void setUp() {
        customer = new Customer();
    }

    @Test
    public void testUserId() {
        Long id = 1L;
        customer.setUserId(id);
        assertEquals(id, customer.getUserId());
    }

    @Test
    public void testUserName() {
        String name = "Test User";
        customer.setUserName(name);
        assertEquals(name, customer.getUserName());
    }

    @Test
    public void testEmail() {
        String email = "test@example.com";
        customer.setEmail(email);
        assertEquals(email, customer.getEmail());
    }

    @Test
    public void testPassword() {
        String password = "password";
        customer.setPassword(password);
        assertEquals(password, customer.getPassword());
    }

    @Test
    public void testCrib() {
        Crib crib = new Crib();
        customer.setCrib(crib);
        assertEquals(crib, customer.getCrib());
    }

    @Test
    public void testTasks() {
        List<Task> tasks = new ArrayList<>();
        customer.setTasks(tasks);
        assertEquals(tasks, customer.getTasks());
    }

    @Test
    public void testConstructor() {
        Crib crib = new Crib();
        List<Task> tasks = new ArrayList<>();
        String name = "Test User";
        String email = "test@example.com";
        String password = "password";
        Customer customer = new Customer(name, email, password, crib);
        assertNotNull(customer);
        assertEquals(name, customer.getUserName());
        assertEquals(email, customer.getEmail());
        assertEquals(password, customer.getPassword());
        assertEquals(crib, customer.getCrib());
        assertEquals(tasks, customer.getTasks());
    }
}