package com.cribhub.backend.controllers;

import com.cribhub.backend.DTO.TaskDTO;
import com.cribhub.backend.domain.Task;
import com.cribhub.backend.services.TaskService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@CrossOrigin
@Log4j2
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/{customerId}/{cribId}")
    public ResponseEntity<TaskDTO> createTask(@PathVariable Long cribId, @PathVariable Long customerId, @RequestBody Task task) {
        Task savedTaskList = taskService.createTask(cribId, customerId, task );

       log.info("Task created: id-{} name-{} description-{}", savedTaskList.getTaskId(), savedTaskList.getTaskName(), savedTaskList.getDescription());
        return ResponseEntity.status(HttpStatus.CREATED).body(TaskDTO.TaskUpdateDTO(savedTaskList));

    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long taskId) {
        Task task = taskService.getTaskById(taskId);
        if (task == null){
            log.error("Task with id {} not found", taskId);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(TaskDTO.TaskUpdateDTO(task));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        log.warn("Task with id {} deleted", taskId);
        return ResponseEntity.noContent().build();
    }

}