package com.javatech.javamail.controllers;

import com.javatech.javamail.dtos.LoginDto;
import com.javatech.javamail.dtos.RegisterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.javatech.javamail.models.User;
import com.javatech.javamail.services.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        Optional<User> user = userService.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.save(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody LoginDto loginDto) {
        Optional<User> userOptional = Optional.ofNullable(userService.findByUsername(loginDto.getUsername()));
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
        User loginUser = userOptional.get();
        if (passwordEncoder.matches(loginDto.getPassword(), loginUser.getPassword())) {
            return ResponseEntity.ok(loginUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> loginUser(@RequestBody RegisterDto dto) {
        Optional<User> userOptional = Optional.ofNullable(userService.findByUsername(dto.getUsername()));
        if (userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User " + userOptional.get().getUsername() + " already exists");
        } else {
            User user = new User();
            user.setUsername(dto.getUsername());
            user.setEmail(dto.getEmail());
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            User createdUser = userService.save(user);
            return ResponseEntity.ok(createdUser);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User userDetails) {
        Optional<User> optionalUser = userService.findById(id);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User user = optionalUser.get();
        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        user.setDeleted(userDetails.isDeleted());
        User updatedUser = userService.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}