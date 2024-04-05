package com.cribhub.backend.repositories;


import com.cribhub.backend.domain.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long> {

}
