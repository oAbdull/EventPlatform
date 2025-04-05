package org.example.userservice.controller;

import org.example.userservice.config.JwtTokenUtil;
import org.example.userservice.dtos.LoginRecord;
import org.example.userservice.model.Response;
import org.example.userservice.model.User;
import org.example.userservice.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/users")
public class UsersController {

    private static final Logger log = LoggerFactory.getLogger(UsersController.class);

    private final AuthenticationManager authManager;
    private final JwtTokenUtil jwtUtil;
    private final IUserService uservice;

    @Autowired
    public UsersController(AuthenticationManager authManager, JwtTokenUtil jwtUtil, IUserService uservice) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.uservice = uservice;
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateUser(@RequestBody LoginRecord dto) {
        log.info("Login request received: userid={}, pwd={}", dto.userid(), dto.pwd());
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.userid(), dto.pwd())
            );
            log.info("Authentication succeeded for: {}", dto.userid());
            User user = (User) authentication.getPrincipal();
            String accessToken = jwtUtil.generateAccessToken(user);
            return ResponseEntity.ok(Response.success(accessToken));
        } catch (Exception e) {
            log.error("Authentication failed for {}: {}", dto.userid(), e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Response.error("Invalid credentials"));
        }
    }


    @GetMapping("/validate-test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("Validate endpoint is working!");
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) throws Exception {
        log.info("Registering user: {}", user.getUserid());
        uservice.saveuser(user);
        return ResponseEntity.ok(Response.success("Registered successfully"));
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile() {
        log.info("Getting user profile");
        return ResponseEntity.ok(Response.success(uservice.getAuthenticatedUser()));
    }
}