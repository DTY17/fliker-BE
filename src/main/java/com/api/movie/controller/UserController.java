package com.api.movie.controller;

import com.api.movie.dto.LoginRequestDto;
import com.api.movie.entity.User;
import com.api.movie.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
//@PreAuthorize("hasRole('ADMIN')")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody User user) {
        log.info("Signup request received for email: {}", user.getEmail());
        User savedUser = userService.signup(user);
        log.debug("User saved with ID: {}", savedUser.getId());
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto req) {
        log.info("Login attempt for email: {}", req.getEmail());
        String token = userService.login(req);
        log.info("Login successful, JWT generated for email: {}", req.getEmail());
        return ResponseEntity.ok(token);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
