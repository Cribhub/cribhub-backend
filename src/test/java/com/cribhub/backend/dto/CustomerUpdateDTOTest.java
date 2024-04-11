package com.cribhub.backend.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerUpdateDTOTest {

    private CustomerUpdateDTO customerUpdateDTO;

    @BeforeEach
    public void setUp() {
        String userName = "Test User";
        String email = "testuser@example.com";
        String password = "testpassword";
        customerUpdateDTO = new CustomerUpdateDTO(userName, email, password);
    }

    @Test
    public void testUserName() {
        String userName = "Test User";
        assertEquals(userName, customerUpdateDTO.getUserName());
    }

    @Test
    public void testEmail() {
        String email = "testuser@example.com";
        assertEquals(email, customerUpdateDTO.getEmail());
    }

    @Test
    public void testPassword() {
        String password = "testpassword";
        assertEquals(password, customerUpdateDTO.getPassword());
    }

    @Test
    public void testSetUserName() {
        String newUserName = "New Test User";
        customerUpdateDTO.setUserName(newUserName);
        assertEquals(newUserName, customerUpdateDTO.getUserName());
    }

    @Test
    public void testSetEmail() {
        String newEmail = "newtestuser@example.com";
        customerUpdateDTO.setEmail(newEmail);
        assertEquals(newEmail, customerUpdateDTO.getEmail());
    }

    @Test
    public void testSetPassword() {
        String newPassword = "newtestpassword";
        customerUpdateDTO.setPassword(newPassword);
        assertEquals(newPassword, customerUpdateDTO.getPassword());
    }
}