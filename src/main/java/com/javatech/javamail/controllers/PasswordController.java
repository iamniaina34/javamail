package com.javatech.javamail.controllers;

import com.javatech.javamail.dtos.ResetDto;
import com.javatech.javamail.models.User;
import com.javatech.javamail.services.EmailService;
import com.javatech.javamail.services.PasswordService;
import com.javatech.javamail.services.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/api/password")
public class PasswordController {

    @Autowired
    private final UserService userService;
    @Autowired
    private final EmailService emailService;
    @Autowired
    private final PasswordService passwordService;

    public PasswordController(UserService userService, EmailService emailService, PasswordService passwordService) {
        this.userService = userService;
        this.emailService = emailService;
        this.passwordService = passwordService;
    }

    @PostMapping("/reset")
    public ResponseEntity<Object> resetPassword(@RequestBody ResetDto dto) {
        User user = userService.findByEmail(dto.getEmail());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        } else {
            String token = passwordService.createPasswordResetToken(user);
            String resetLink = "http://192.168.48.49:8000/confirmer-reinitialiser/" + token;
            emailService.sendPasswordResetEmail(dto.getEmail(), resetLink);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
    }

    @GetMapping("/confirm-reset/{token}")
    public ResponseEntity<Object> confirmReset(@PathVariable String token) {
        boolean isValidToken = passwordService.validateResetPasswordToken(token);
        if (isValidToken) {
            User user = passwordService.getUserByResetToken(token);
            System.out.println(user);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or Expired token");
        }
    }

    private static @NotNull String getResetLink(String token) {
        StringBuilder ipAddress = new StringBuilder();
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            ipAddress.append(localHost.getHostAddress());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        return "http://" + ipAddress + ":8080/api/password/confirm-reset/" + token;
    }
}
