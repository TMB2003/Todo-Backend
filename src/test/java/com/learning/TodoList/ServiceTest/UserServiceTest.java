package com.learning.TodoList.ServiceTest;

import com.learning.TodoList.entities.User;
import com.learning.TodoList.repositories.TaskRepository;
import com.learning.TodoList.repositories.UserRepository;
import com.learning.TodoList.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Mock
    private TaskRepository taskRepository;

    @Test
    public void createUser_Test(){
        User user = new User();
        user.setName("Test");
        user.setEmail("test@gmail.com");
        user.setPassword("password");

        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.createUser(user);

        verify(passwordEncoder).encode("password");
        verify(userRepository).save(user);
    }

    @Test
    public void findUser_WhenUserExists_ShouldReturnUser(){
        // ARRANGE
        User fakeUser = new User();
        fakeUser.setEmail("taha@gmail.com");

        when(userRepository.findByEmail("taha@gmail.com"))
                .thenReturn(fakeUser);

        User result = userService.findUser("taha@gmail.com");

        assertNotNull(result);
        assertEquals("taha@gmail.com", result.getEmail());
    }

    @Test
    public void findUser_WhenUserNotFound_ShouldReturnNull(){
        // ARRANGE
        when(userRepository.findByEmail("notfound@gmail.com"))
                .thenReturn(null);

        User result = userService.findUser("notfound@gmail.com");

        assertNull(result);
    }

    @Test
    void deleteUser_ShouldDeleteTasksAndUser(){
        User fakeUser = new User();
        fakeUser.setEmail("taha@gmail.com");
        fakeUser.setId("12");

        when(userRepository.findByEmail("taha@gmail.com"))
                .thenReturn(fakeUser);

        userService.deleteUser("taha@gmail.com");

        verify(taskRepository).deleteByUserId("12");
        verify(userRepository).deleteById("12");
    }

    @Test
    void updateUser_ShouldEncodeNewPassword(){
        User oldUser = new User();
        oldUser.setEmail("taha@gmail.com");
        oldUser.setPassword("oldPassword");

        User newUser = new User();
        newUser.setPassword("newPassword");

        when(userRepository.findByEmail("taha@gmail.com"))
                .thenReturn(oldUser);
        when(passwordEncoder.encode("newPassword"))
                .thenReturn("encodedNewPassword");
        when(userRepository.save(any(User.class)))
                .thenReturn(oldUser);

        // ACT
        userService.updateUserByName("taha@gmail.com", newUser);

        // ASSERT
        verify(passwordEncoder).encode("newPassword");
        verify(userRepository).save(oldUser);
        assertEquals("encodedNewPassword", oldUser.getPassword());
    }
}
