package com.cribhub.backend;

import com.cribhub.backend.domain.TodoItem;
import com.cribhub.backend.repositories.TodoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Container;

@Component
@ActiveProfiles("test")
public class TestDataLoader implements CommandLineRunner {

    private final TodoRepository todos;

    public TestDataLoader(TodoRepository todos) {
        this.todos = todos;
    }

    @Override
    public void run(String... args) throws Exception {
        TodoItem todoItem = new TodoItem();
        todoItem.setId(1L);
        todoItem.setTitle("First Todo Item");
        todoItem.setCompleted(false);
        todos.save(todoItem);
    }
}
