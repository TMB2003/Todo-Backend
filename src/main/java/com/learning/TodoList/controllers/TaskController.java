package com.learning.TodoList.controllers;

import com.learning.TodoList.entities.Task;
import com.learning.TodoList.enums.Status;
import com.learning.TodoList.services.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        try {
            Task mytask = taskService.createTask(task, email);
            return new ResponseEntity<>(mytask, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Task not Created: ", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<Task> changeStatus(@PathVariable String id){
        Task task = taskService.taskCompleted(id);
        if(task != null){
            return new ResponseEntity<>(task, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable String id, @RequestBody Task task){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        try{
            Task newTask = taskService.updateTask(task, id, email);
            return new ResponseEntity<>(newTask, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Not Updated: ", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable String id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        try{
            taskService.deleteTask(id, email);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("Task not deleted: " , e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) LocalDate deadline){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        List<Task> all = taskService.getAllTasks(email, status, deadline);
        if(all == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(all, HttpStatus.OK);
    }
}
