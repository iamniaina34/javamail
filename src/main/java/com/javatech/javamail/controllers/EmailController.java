package com.javatech.javamail.controllers;

import com.javatech.javamail.dtos.ConfirmEmailDto;
import com.javatech.javamail.dtos.EmailDto;
import com.javatech.javamail.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/sendPinCodeEmail")
    public String sendPinCodeEmail(@RequestBody EmailDto emailDto) {
        emailService.sendPinCodeMessage(emailDto.getTo(), "Confirmer votre email");
        return "Confirmation email sent successfully";
    }

    @PostMapping("/confirmPinCodeEmail")
    public ResponseEntity<Object> confirmPinCodeEmail(@RequestBody ConfirmEmailDto confirmEmailDto) {
        return emailService.confirmPinCodeMessage(confirmEmailDto.getFrom(), confirmEmailDto.getPinCode());
    }
}