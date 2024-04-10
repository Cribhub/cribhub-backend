package com.cribhub.backend.services.intefaces;


import com.cribhub.backend.domain.Task;

public interface TaskService {
    Task saveTask(Task task);

    Task createTask(Long cribId, Long customerId, Task task);
    Task getTaskById(Long taskId);
    void deleteTask(Long taskId);
}