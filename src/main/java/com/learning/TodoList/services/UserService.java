package com.learning.TodoList.services;

import com.learning.TodoList.entities.User;
import com.learning.TodoList.repositories.TaskRepository;
import com.learning.TodoList.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return saveUser(user);
    }

    public User findUser(String email){
        return userRepository.findByEmail(email);
    }

    public User updateUserByName(String email, User newUser){
        User oldUser = userRepository.findByEmail(email);

        if(oldUser == null) return null;

        if(newUser.getName() != null){
            oldUser.setName(newUser.getName());
        }

        if(newUser.getPassword() != null){
            oldUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        }
        return saveUser(oldUser);
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public void deleteUser(String email){
        User user = findUser(email);
        taskRepository.deleteByUserId(user.getId());
        userRepository.deleteById(user.getId());
    }
}
