package com.cribhub.backend.controllers;

import com.cribhub.backend.DTO.TaskDTO;
import com.cribhub.backend.domain.Task;
import com.cribhub.backend.services.CribService;
import com.cribhub.backend.services.CustomerService;
import com.cribhub.backend.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@CrossOrigin
public class TaskController {

    private final TaskService taskService;
    private final CribService cribService;
    private final CustomerService customerService;

    @Autowired
    public TaskController(TaskService taskService, CribService cribService, CustomerService customerService) {
        this.taskService = taskService;
        this.cribService = cribService;
        this.customerService = customerService;
    }

    @PostMapping("/{customerId}/{cribId}")
    public ResponseEntity<TaskDTO> createTask(@PathVariable Long cribId, @PathVariable Long customerId, @RequestBody Task task) {
        Task savedTaskList = taskService.createTask(cribId, customerId, task );
        return ResponseEntity.status(HttpStatus.CREATED).body(TaskDTO.TaskUpdateDTO(savedTaskList));

    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long taskId) {
        Task task = taskService.getTaskById(taskId);
        if (task == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(TaskDTO.TaskUpdateDTO(task));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

}