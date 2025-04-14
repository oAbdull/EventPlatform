package org.userservice.controller;

import org.userservice.dtos.LoginDto;
import org.userservice.model.User;
import org.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @GetMapping("/list")
    public ResponseEntity<List<User>> listAll() {
        List<User> users = userService.listAll();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto user) {
        User foundUser = userService.getUserByIdAndPwd(user.getUserid(), user.getPwd());
        if (foundUser != null) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
