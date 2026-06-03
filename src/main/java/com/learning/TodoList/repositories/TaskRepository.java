package com.learning.TodoList.repositories;

import com.learning.TodoList.entities.Task;
import com.learning.TodoList.enums.Status;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByUserId(String userId);
    List<Task> findByUserIdAndStatus(String userId, Status status);
    List<Task> findByUserIdAndDeadLineBefore(String userId, LocalDate deadline);
    List<Task> findByUserIdAndStatusAndDeadLineBefore(String userId, Status status, LocalDate deadline);

    void deleteByUserId(String userId);
}
