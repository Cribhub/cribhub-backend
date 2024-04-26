package com.cribhub.backend.services;

import com.cribhub.backend.domain.Crib;
import com.cribhub.backend.domain.Customer;
import com.cribhub.backend.domain.Notification;
import com.cribhub.backend.domain.Task;
import com.cribhub.backend.repositories.CribRepository;
import com.cribhub.backend.repositories.CustomerRepository;
import com.cribhub.backend.repositories.NotificationRepostitory;
import com.cribhub.backend.repositories.TaskRepository;
import com.cribhub.backend.services.intefaces.TaskService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final CribRepository cribRepository;
    private final CustomerRepository customerRepository;

    private final NotificationRepostitory notificationRepostitory;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, CribRepository cribRepository,
            CustomerRepository customerRepository, NotificationRepostitory notificationRepostitory) {
        this.taskRepository = taskRepository;
        this.cribRepository = cribRepository;
        this.customerRepository = customerRepository;
        this.notificationRepostitory = notificationRepostitory;
    }

    @Transactional
    public Task createTask(Long cribId, Long customerId, Task task) {
        Crib crib = cribRepository.findById(cribId)
                .orElseThrow(() -> new RuntimeException("Crib not found with id " + cribId));
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with id " + customerId));

        Notification notification = new Notification();
        notification.setCustomer(customer);
        notification.setName("Task created: " + task.getTitle());
        notification.setDescription(task.getDescription());
        notificationRepostitory.save(notification);

        task.setCrib(crib);
        task.setCustomer(customer);
        return taskRepository.save(task);
    }

    @Override
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task getTaskById(Long taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        return task.orElse(null);
    }

    @Override
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

}
