package com.cribhub.backend.DTO;

import com.cribhub.backend.domain.Crib;
import com.cribhub.backend.domain.Customer;
import com.cribhub.backend.domain.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerDTOTest {

    private Customer customer;
    private CustomerDTO customerDTO;

    @BeforeEach
    public void setUp() {
        customer = new Customer();
        customer.setUserId(1L);
        customer.setUserName("Test User");
        customer.setEmail("testuser@example.com");

        Crib crib = new Crib();
        crib.setCribId(1L);
        customer.setCrib(crib);

        List<Customer> cribMembers = new ArrayList<>();
        cribMembers.add(customer);
        crib.setCribMembers(cribMembers);

        List<Task> tasks = new ArrayList<>();
        Task task = new Task();
        task.setTaskId(1L);
        tasks.add(task);
        customer.setTasks(tasks);

        customerDTO = CustomerDTO.ConvertToDTO(customer);
    }

    @Test
    public void testUserId() {
        assertEquals(customer.getUserId(), customerDTO.getUserId());
    }

    @Test
    public void testUserName() {
        assertEquals(customer.getUserName(), customerDTO.getUserName());
    }

    @Test
    public void testEmail() {
        assertEquals(customer.getEmail(), customerDTO.getEmail());
    }

    @Test
    public void testCribId() {
        assertEquals(customer.getCrib().getCribId(), customerDTO.getCribId());
    }
}