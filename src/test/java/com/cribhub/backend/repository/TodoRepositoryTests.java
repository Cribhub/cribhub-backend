package com.cribhub.backend.repository;

import com.cribhub.backend.repositories.TodoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class TodoRepositoryTests {

    static {
        if (System.getenv("HEROKU_TEST_RUN_BRANCH") != null) {
            System.setProperty("TESTCONTAINERS_RYUK_DISABLED", "true");
            System.setProperty("DOCKER_HOST", "tcp://localhost:2375");
        }
    }

    @Autowired
    TodoRepository todos;

    @Test
    void shouldReturnAllTodos() {
        long count = todos.findAll().size();
        assertEquals(1, count);
    }
}
