package com.javatech.javamail.controllers;

import com.javatech.javamail.dtos.ChangePasswordDto;
import com.javatech.javamail.dtos.ResetDto;
import com.javatech.javamail.models.User;
import com.javatech.javamail.services.EmailService;
import com.javatech.javamail.services.PasswordService;
import com.javatech.javamail.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/password")
public class PasswordController {

    @Autowired
    private final UserService userService;
    @Autowired
    private final EmailService emailService;
    @Autowired
    private final PasswordService passwordService;
    @Autowired
    private final PasswordEncoder encoder;

    public PasswordController(UserService userService, EmailService emailService, PasswordService passwordService, PasswordEncoder encoder) {
        this.userService = userService;
        this.emailService = emailService;
        this.passwordService = passwordService;
        this.encoder = encoder;
    }

    @PostMapping({"/reset", "/reset/"})
    public ResponseEntity<Object> resetPassword(@RequestBody ResetDto dto) {
        User user = userService.findByEmail(dto.getEmail());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        } else {
            String token = passwordService.createPasswordResetToken(user);
            String resetLink = dto.getClientAddress() + "/" + token;
            try {
                emailService.sendPasswordResetEmail(dto.getEmail(), resetLink);
            } catch (MailSendException e) {
                return ResponseEntity.internalServerError().body("Failed to connect to host, try again later");
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    @PutMapping({"", "/", "/change-password"})
    public ResponseEntity<Object> changePassword(@RequestBody ChangePasswordDto dto) {
        User user = userService.findByEmail(dto.getTo());
        if (user != null) {
            passwordService.updateUserPassword(user, encoder.encode(dto.getPassword()));
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
    }
}
