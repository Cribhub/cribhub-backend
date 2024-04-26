package com.cribhub.backend.services;

import com.cribhub.backend.domain.Crib;
import com.cribhub.backend.domain.Customer;
import com.cribhub.backend.domain.Notification;
import com.cribhub.backend.domain.Task;
import com.cribhub.backend.repositories.CribRepository;
import com.cribhub.backend.repositories.CustomerRepository;
import com.cribhub.backend.repositories.NotificationRepostitory;
import com.cribhub.backend.repositories.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class TaskServiceTests {

    @InjectMocks
    TaskServiceImpl taskService;
    @Mock
    NotificationRepostitory notificationRepostitory;

    @Mock
    TaskRepository taskRepository;

    @Mock
    CribRepository cribRepository;

    @Mock
    CustomerRepository customerRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createTaskTest() {
        Crib crib = new Crib();
        Customer customer = new Customer();
        Task task = new Task();
        Notification notification = new Notification();
        customer.setNotifications(new ArrayList<>());

        when(cribRepository.findById(1L)).thenReturn(Optional.of(crib));
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(taskRepository.save(task)).thenReturn(task);
        when(notificationRepostitory.save(notification)).thenReturn(notification);

        //when(notificationRepostitory.save(any())).thenReturn(new Task().getDescription());

        Task result = taskService.createTask(1L, 1L, task);

        assertNotNull(result);
        assertEquals(task, result);
        verify(cribRepository, times(1)).findById(1L);
        verify(customerRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void saveTaskTest() {
        Task task = new Task();

        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskService.saveTask(task);

        assertNotNull(result);
        assertEquals(task, result);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void getTaskByIdTest() {
        Task task = new Task();

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task result = taskService.getTaskById(1L);

        assertNotNull(result);
        assertEquals(task, result);
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    public void deleteTaskTest() {
        doNothing().when(taskRepository).deleteById(1L);

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).deleteById(1L);
    }
}