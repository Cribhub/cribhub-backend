package com.cribhub.backend.controllers;

import com.cribhub.backend.domain.ShoppingListItem;
import com.cribhub.backend.domain.Task;
import com.cribhub.backend.dto.TaskDTO;
import com.cribhub.backend.services.intefaces.TaskService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

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
    public ResponseEntity<TaskDTO> createTask(@PathVariable Long cribId, @PathVariable Long customerId,
            @RequestBody Task task) {
        task.setCompleted(false);
        Task savedTaskList = taskService.createTask(cribId, customerId, task);

        log.info("Task created: id-{} name-{} description-{}", savedTaskList.getTaskId(), savedTaskList.getTitle(),
                savedTaskList.getDescription());
        return ResponseEntity.status(HttpStatus.CREATED).body(TaskDTO.TaskUpdateDTO(savedTaskList));

    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long taskId) {
        Task task = taskService.getTaskById(taskId);
        if (task == null) {
            log.error("Task with id {} not found", taskId);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(TaskDTO.TaskUpdateDTO(task));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long taskId, @RequestBody Task task){
        log.info("Updating task list with id {}", taskId);

        Optional<Task> taskOptional = Optional.ofNullable(taskService.getTaskById(taskId));
        if (taskOptional.isEmpty()) {
            log.error("Can not update task list item with id {} because it does not exist.", taskId);
            return ResponseEntity.notFound().build();
        }

        Task existingTask = taskOptional.get();

        if (Objects.nonNull(task.getTitle())){
            existingTask.setTitle(task.getTitle());}
        if (Objects.nonNull(task.getDescription())){
            existingTask.setDescription(task.getDescription());}
        if (Objects.nonNull(task.getDeadlineDate())){
            existingTask.setDeadlineDate(task.getDeadlineDate());}
        if (Objects.nonNull(task.getCustomer())){
            existingTask.setCustomer(task.getCustomer());}
        if (Objects.nonNull(task.getCompleted())){
            existingTask.setCompleted(task.getCompleted());}

        Task updatedTask = taskService.saveTask(existingTask);

        log.info("Shopping list item updated: id-{} name-{}", task, updatedTask.getTitle());
        return ResponseEntity.ok(TaskDTO.TaskUpdateDTO(updatedTask));

    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        log.warn("Task with id {} deleted", taskId);
        return ResponseEntity.noContent().build();
    }

}
