package com.learning.TodoList.ServiceTest;

import com.learning.TodoList.entities.Task;
import com.learning.TodoList.entities.User;
import com.learning.TodoList.enums.Status;
import com.learning.TodoList.repositories.TaskRepository;
import com.learning.TodoList.services.TaskService;
import com.learning.TodoList.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrackServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Mock
    private UserService userService;


    @Test
    public void test_createTask(){
        User user = new User();
        user.setId("12");
        user.setEmail("test@gmail.com");

        Task task = new Task();
        task.setId("12");

        when(userService.findUser("test@gmail.com")).thenReturn(user);
        when(taskRepository.save(task)).thenReturn(task);

        Task newTask = taskService.createTask(task, "test@gmail.com");

        verify(userService).findUser("test@gmail.com");
        assertEquals(Status.PENDING, newTask.getStatus());
        verify(taskRepository).save(task);
    }

    @Test
    public void test_updateTask(){
        User user = new User();
        user.setId("12");
        user.setEmail("test@gmail.com");

        Task oldTask = new Task();
        oldTask.setId("123");
        oldTask.setUserId("12");

        Task newTask = new Task();
        newTask.setName("test");
        newTask.setUserId("12");
        newTask.setId("123");
        newTask.setDescription("testing");
        newTask.setStatus(Status.COMPLETE);
        newTask.setDeadLine(LocalDate.of(2026, 6, 10));

        when(userService.findUser("test@gmail.com")).thenReturn(user);
        when(taskRepository.findById("123")).thenReturn(Optional.of(oldTask));
        when(taskRepository.save(oldTask)).thenReturn(oldTask);

        Task task = taskService.updateTask(newTask, "123", "test@gmail.com");

        verify(userService).findUser("test@gmail.com");
        assertEquals(newTask, task);
        verify(taskRepository).save(newTask);
    }

    @Test
    public void test_updateTask_ReturnOldTaskNull(){
        Task newTask = new Task();
        newTask.setName("test");
        newTask.setUserId("12");

        when(taskRepository.findById("123")).thenReturn(Optional.empty());

        Task task = taskService.updateTask(newTask, "123", "test@gmail.com");

        assertNull(task);
        verify(taskRepository).findById("123");
        verifyNoInteractions(userService);
    }

    @Test
    public void test_updateTask_ReturnUserNull(){
        Task oldTask = new Task();
        oldTask.setId("123");
        oldTask.setUserId("12");

        Task newTask = new Task();
        newTask.setName("test");
        newTask.setUserId("12");

        when(userService.findUser("test@gmail.com")).thenReturn(null);
        when(taskRepository.findById("123")).thenReturn(Optional.of(oldTask));

        Task task = taskService.updateTask(newTask, "123", "test@gmail.com");

        verify(userService).findUser("test@gmail.com");
        assertNull(task);
    }

    @Test
    public void test_deleteTask(){
        Task task = new Task();
        task.setId("123");
        task.setUserId("12");


        when(taskRepository.findById("123")).thenReturn(Optional.of(task));

        User user = new User();
        user.setId("12");
        user.setEmail("test@gmail.com");

        when(userService.findUser("test@gmail.com")).thenReturn(user);

        taskService.deleteTask("123", "test@gmail.com");

        verify(taskRepository).deleteById("123");
    }

    @Test
    public void test_deleteTask_TaskIdNull(){
        when(taskRepository.findById("123")).thenReturn(Optional.empty());

        taskService.deleteTask("123", "test@gmail.com");

        verifyNoInteractions(userService);
        verify(taskRepository, never()).deleteById("12");
    }

    @Test
    public void test_deleteTask_UserNull(){
        Task task = new Task();
        task.setId("123");
        task.setUserId("12");


        when(taskRepository.findById("123")).thenReturn(Optional.of(task));

        when(userService.findUser("test@gmail.com")).thenReturn(null);

        taskService.deleteTask("123", "test@gmail.com");

        verify(taskRepository, never()).deleteById("123");
    }
}
