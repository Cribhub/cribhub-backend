package com.cribhub.backend.dto;

import com.cribhub.backend.domain.Crib;
import com.cribhub.backend.domain.Customer;
import com.cribhub.backend.domain.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskDTOTest {

    private Task task;
    private TaskDTO taskDTO;

    @BeforeEach
    public void setUp() {
        task = new Task();
        task.setTaskId(1L);
        task.setTitle("Test Task");
        task.setDescription("Test Description");

        Crib crib = new Crib();
        crib.setCribId(1L);
        task.setCrib(crib);

        Customer customer = new Customer();
        customer.setUserId(1L);
        task.setCustomer(customer);

        taskDTO = TaskDTO.TaskUpdateDTO(task);
    }

    @Test
    public void testTaskId() {
        assertEquals(task.getTaskId(), taskDTO.getTaskId());
    }

    @Test
    public void testTaskName() {
        assertEquals(task.getTitle(), taskDTO.getTaskName());
    }

    @Test
    public void testDescription() {
        assertEquals(task.getDescription(), taskDTO.getDescription());
    }

    @Test
    public void testCribId() {
        assertEquals(task.getCrib().getCribId(), taskDTO.getCribId());
    }

    @Test
    public void testCustomerId() {
        assertEquals(task.getCustomer().getUserId(), taskDTO.getCustomerId());
    }
}