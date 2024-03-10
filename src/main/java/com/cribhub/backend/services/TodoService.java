package com.cribhub.backend.services;

import com.cribhub.backend.domain.TodoItem;
import com.cribhub.backend.repositories.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService implements ITodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public TodoItem findById(Long id) {
        return todoRepository.findById(id).orElse(null);
    }

    @Override
    public TodoItem addTodo(TodoItem todoItem) {
        return todoRepository.save(todoItem);
    }

    @Override
    public List<TodoItem> getAllTodos() {
        return todoRepository.findAll();
    }
}
