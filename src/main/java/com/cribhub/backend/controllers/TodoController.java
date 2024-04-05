package com.cribhub.backend.controllers;

import com.cribhub.backend.services.ITodoService;
import com.cribhub.backend.domain.TodoItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TodoController {
    private final ITodoService todoService;

    public TodoController(ITodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/todo/{id}")
    public TodoItem getTodoById(@PathVariable long id) {
        return todoService.findById(id);
    }

    @PostMapping("/todo")
    public ResponseEntity<TodoItem> addTodo(@RequestBody TodoItem todoItem) {
        TodoItem savedTodoItem = todoService.addTodo(todoItem);
        return new ResponseEntity<>(savedTodoItem, HttpStatus.CREATED);
    }

    @GetMapping("/todos")
    public ResponseEntity<Iterable<TodoItem>> getAllTodos() {
        return new ResponseEntity<>(todoService.getAllTodos(), HttpStatus.OK);
    }

    @DeleteMapping("/todo/{id}")
    public ResponseEntity<Void> deleteTodoById(@PathVariable long id) {
        var success = todoService.deleteById(id);
        if (!success) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
