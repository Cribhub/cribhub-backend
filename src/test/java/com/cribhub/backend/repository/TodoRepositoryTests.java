package com.cribhub.backend.repository;

import com.cribhub.backend.repositories.TodoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class TodoRepositoryTests {
    @Autowired
    TodoRepository todos;

    @Test
    void shouldReturnAllTodos() {
        long count = todos.findAll().size();
        assertEquals(1, count);
    }
}
