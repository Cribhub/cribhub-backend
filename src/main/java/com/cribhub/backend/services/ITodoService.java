package com.cribhub.backend.services;

import com.cribhub.backend.domain.TodoItem;

import java.util.List;

public interface ITodoService {
    public TodoItem findById(Long id);
    public TodoItem addTodo(TodoItem todoItem);

    public List<TodoItem> getAllTodos();
}
