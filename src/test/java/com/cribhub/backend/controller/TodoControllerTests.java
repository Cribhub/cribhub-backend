package com.cribhub.backend.controller;

import com.cribhub.backend.controllers.TodoController;
import com.cribhub.backend.domain.TodoItem;
import com.cribhub.backend.services.ITodoService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TodoControllerTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void shouldFindAllTodos() {
        ResponseEntity<List<TodoItem>> exchange = restTemplate.exchange("/todos", HttpMethod.GET, null, new ParameterizedTypeReference<List<TodoItem>>() {});
        assertNotNull(exchange);
        List<TodoItem> todos = exchange.getBody();
        assert todos != null;
        assertEquals(1,todos.size());
        assertEquals( 1,todos.get(0).getId());
    }

    @Test
    void shouldFindOneValidTodoItem() {
        ResponseEntity<TodoItem> entity = restTemplate.getForEntity("/todo/1", TodoItem.class);
        assertEquals(HttpStatus.OK,entity.getStatusCode());
        TodoItem todo = entity.getBody();
        assert todo != null;
        assertEquals("First Todo Item", todo.getTitle());
    }

}
