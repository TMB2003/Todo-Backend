package com.learning.TodoList.services;

import com.learning.TodoList.entities.Task;
import com.learning.TodoList.entities.User;
import com.learning.TodoList.enums.Status;
import com.learning.TodoList.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public Task createTask(Task task, String email){
        task.setEntryTime(LocalDateTime.now());
        User user = userService.findUser(email);
        if(user == null) return null;

        task.setUserId(user.getId());
        task.setStatus(Status.PENDING);
        taskRepository.save(task);

        user.getTaskList().add(task);
        userService.saveUser(user);

        return task;
    }

    public Task findTask(String id) {
        return taskRepository.findById(id).orElse(null);
    }

    public Task saveTask(Task task){
        return taskRepository.save(task);
    }

    public Task taskCompleted(String id) {
        Task task = findTask(id);
        if(task == null) return task;

        task.setStatus((task.getStatus() == Status.PENDING) ? Status.COMPLETE : Status.PENDING);
        return saveTask(task);
    }

    public Task updateTask(Task newtask, String id){
        Task oldTask = findTask(id);
        if(oldTask == null) return oldTask;

        if(newtask.getName() != null) oldTask.setName(newtask.getName());
        if(newtask.getDescription() != null) oldTask.setDescription(newtask.getDescription());
        if(newtask.getStatus() != null) oldTask.setStatus(newtask.getStatus());
        if(newtask.getDeadLine() != null) oldTask.setDeadLine(newtask.getDeadLine());

        return saveTask(oldTask);
    }

    public boolean deleteTask(String id, String email){
        Task task = findTask(id);
        if(task == null) return false;

        User user = userService.findUser(email);
        if(user == null) return false;

        Boolean removed = user.getTaskList().removeIf(x -> x.getId().equals(id));
        if(removed) userService.saveUser(user);
        return removed;
    }
}
