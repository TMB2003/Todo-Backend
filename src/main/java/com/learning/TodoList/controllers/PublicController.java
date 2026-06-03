package com.learning.TodoList.controllers;

import com.learning.TodoList.entities.User;
import com.learning.TodoList.services.UserDetailsServiceImpl;
import com.learning.TodoList.services.UserService;
import com.learning.TodoList.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/public")
public class PublicController {

    @GetMapping("/health-check")
    public String health(){return "OK";}

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsServiceImpl userDetailService;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<String> Register(@RequestBody User newUser){
        try {
            userService.createUser(newUser);
            String jwt = jwtUtils.generateToken(newUser.getEmail());
            return new ResponseEntity<>(jwt, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> Login(@RequestBody User user){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));
            UserDetails userDetails = userDetailService.loadUserByUsername(user.getEmail());
            String jwt = jwtUtils.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occurred while createAuthenticationToken ", e);
            return new ResponseEntity<>("Invalid Email or Password", HttpStatus.UNAUTHORIZED);
        }
    }
}
