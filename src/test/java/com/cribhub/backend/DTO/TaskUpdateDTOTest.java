package com.cribhub.backend.DTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskUpdateDTOTest {

    private TaskUpdateDTO taskUpdateDTO;

    @BeforeEach
    public void setUp() {
        Long cribId = 1L;
        String taskName = "Test Task";
        String description = "Test Description";
        Long customerId = 1L;
        taskUpdateDTO = new TaskUpdateDTO(cribId, taskName, description, customerId);
    }

    @Test
    public void testCribId() {
        Long cribId = 1L;
        assertEquals(cribId, taskUpdateDTO.getCribId());
    }

    @Test
    public void testTaskName() {
        String taskName = "Test Task";
        assertEquals(taskName, taskUpdateDTO.getTaskName());
    }

    @Test
    public void testDescription() {
        String description = "Test Description";
        assertEquals(description, taskUpdateDTO.getDescription());
    }

    @Test
    public void testCustomerId() {
        Long customerId = 1L;
        assertEquals(customerId, taskUpdateDTO.getCustomerId());
    }

    @Test
    public void testSetCribId() {
        Long newCribId = 2L;
        taskUpdateDTO.setCribId(newCribId);
        assertEquals(newCribId, taskUpdateDTO.getCribId());
    }

    @Test
    public void testSetTaskName() {
        String newTaskName = "New Test Task";
        taskUpdateDTO.setTaskName(newTaskName);
        assertEquals(newTaskName, taskUpdateDTO.getTaskName());
    }

    @Test
    public void testSetDescription() {
        String newDescription = "New Test Description";
        taskUpdateDTO.setDescription(newDescription);
        assertEquals(newDescription, taskUpdateDTO.getDescription());
    }

    @Test
    public void testSetCustomerId() {
        Long newCustomerId = 2L;
        taskUpdateDTO.setCustomerId(newCustomerId);
        assertEquals(newCustomerId, taskUpdateDTO.getCustomerId());
    }
}