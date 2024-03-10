package com.cribhub.backend;

import com.cribhub.backend.domain.TodoItem;
import com.cribhub.backend.repositories.TodoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Profile({"dev","prod"})
	@Bean
	CommandLineRunner run(TodoRepository todoRepository) {
		return args -> {
			TodoItem todoItem = new TodoItem();
			todoItem.setId(1L);
			todoItem.setTitle("First Todo Item");
			todoItem.setCompleted(false);
			todoRepository.save(todoItem);
		};
	}

}
