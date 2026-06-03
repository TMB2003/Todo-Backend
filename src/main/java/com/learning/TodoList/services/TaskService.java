package com.learning.TodoList.services;

import com.learning.TodoList.entities.Task;
import com.learning.TodoList.entities.User;
import com.learning.TodoList.enums.Status;
import com.learning.TodoList.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public Task createTask(Task task, String email){
        User user = userService.findUser(email);
        if(user == null) return null;

        task.setUserId(user.getId());
        task.setStatus(Status.PENDING);
        taskRepository.save(task);

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
        if(task == null) return null;

        task.setStatus((task.getStatus() == Status.PENDING) ? Status.COMPLETE : Status.PENDING);
        return saveTask(task);
    }

    public Task updateTask(Task newtask, String id, String email){
        Task oldTask = findTask(id);
        if(oldTask == null) return null;

        User user = userService.findUser(email);
        if(!oldTask.getUserId().equals(user.getId())) return null;

        if(newtask.getName() != null) oldTask.setName(newtask.getName());
        if(newtask.getDescription() != null) oldTask.setDescription(newtask.getDescription());
        if(newtask.getStatus() != null) oldTask.setStatus(newtask.getStatus());
        if(newtask.getDeadLine() != null) oldTask.setDeadLine(newtask.getDeadLine());

        return saveTask(oldTask);
    }

    public void deleteTask(String id, String email){
        Task task = findTask(id);
        if(task == null) return;

        User user = userService.findUser(email);
        if(user == null) return;

        taskRepository.deleteById(id);
    }

    public List<Task> getAllTasks(String email, Status status, LocalDate deadline){
        User user = userService.findUser(email);
        if(user == null) return Collections.emptyList();

        List<Task> all = taskRepository.findByUserId(user.getId());
        if(status != null && deadline != null){
            all = taskRepository.findByUserIdAndStatusAndDeadLineBefore(user.getId(), status, deadline);
        }
        else if(status != null){
            all = taskRepository.findByUserIdAndStatus(user.getId(), status);
        }
        else if(deadline != null){
            all = taskRepository.findByUserIdAndDeadLineBefore(user.getId(), deadline);
        }
        return all;
    }
}
