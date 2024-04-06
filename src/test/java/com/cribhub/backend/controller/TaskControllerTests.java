package com.cribhub.backend.controller;

import com.cribhub.backend.DTO.TaskDTO;
import com.cribhub.backend.controllers.TaskController;
import com.cribhub.backend.domain.Crib;
import com.cribhub.backend.domain.Customer;
import com.cribhub.backend.domain.Task;
import com.cribhub.backend.services.CribService;
import com.cribhub.backend.services.CustomerService;
import com.cribhub.backend.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class TaskControllerTests {

    @InjectMocks
    TaskController taskController;

    @Mock
    TaskService taskService;

    @Mock
    CribService cribService;

    @Mock
    CustomerService customerService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createTaskTest() {
        Crib crib = new Crib();
        Customer customer = new Customer();
        Task task = new Task();
        TaskDTO taskDTO = TaskDTO.TaskUpdateDTO(task);

        when(taskService.createTask(1L, 1L, task)).thenReturn(task);

        ResponseEntity<TaskDTO> result = taskController.createTask(1L, 1L, task);

        assertNotNull(result);
        assertThat(result.getBody()).isEqualToComparingFieldByField(taskDTO);
        verify(taskService, times(1)).createTask(1L, 1L, task);
    }

    @Test
    public void getTaskByIdTest() {
        Task task = new Task();
        TaskDTO taskDTO = TaskDTO.TaskUpdateDTO(task);

        when(taskService.getTaskById(1L)).thenReturn(task);

        ResponseEntity<TaskDTO> result = taskController.getTaskById(1L);

        assertNotNull(result);
        assertThat(result.getBody()).isEqualToComparingFieldByField(taskDTO);
        verify(taskService, times(1)).getTaskById(1L);
    }

    @Test
    public void getTaskByIdNotFoundTest() {
        when(taskService.getTaskById(1L)).thenReturn(null);

        ResponseEntity<TaskDTO> result = taskController.getTaskById(1L);

        assertEquals(404, result.getStatusCodeValue());
        verify(taskService, times(1)).getTaskById(1L);
    }

    @Test
    public void deleteTaskTest() {
        doNothing().when(taskService).deleteTask(1L);

        ResponseEntity<Void> result = taskController.deleteTask(1L);

        assertEquals(204, result.getStatusCodeValue());
        verify(taskService, times(1)).deleteTask(1L);
    }
}