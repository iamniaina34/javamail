package com.javatech.javamail.services;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ConcurrentMap;

@Service
public class EmailService {

    @Autowired
    private final JavaMailSender emailSender;

    @Autowired
    private final ConcurrentMap<String, String> pinCodeCache;

    public EmailService(JavaMailSender emailSender, ConcurrentMap<String, String> pinCodeCache) {
        this.emailSender = emailSender;
        this.pinCodeCache = pinCodeCache;
    }

    @Async
    public void sendPinCodeMessage(String to, String subject) throws Exception {
        String pinCode = generatePinCode();
        String htmlContent = "<html>" +
                "<body>" +
                "<p>Bonjour,</p>" +
                "<p>Votre code de confirmation est : <strong>" + pinCode + "</strong></p>" +
                "<p>Merci,</p>" +
                "<p><i>TinyTasker</i></p>" +
                "</body>" +
                "</html>";
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            emailSender.send(message);
            pinCodeCache.put(to, pinCode);
    }

    @Async
    public void sendPasswordResetEmail (String to, String resetLink) throws MailSendException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Reinitialiser votre mot de passe");
        message.setText("Bonjour, \n" +
                "Nous avons entendu que vous avez oublié votre mot de passe. " +
                "Ce n'est pas grave! Vous pouvez le reinitialiser en cliquant sur le lien ci dessous\n" +
                resetLink + "\n\n" +
                "De la part de votre petit equipe, TinyTasker");
        emailSender.send(message);
    }

    private String generatePinCode() {
        Random random = new Random();
        int pin = 100000 + random.nextInt(900000);
        return String.valueOf(pin);
    }

    public ResponseEntity<Object> confirmPinCodeMessage(String from, String pinCode) {
        String savedPinCode = pinCodeCache.get(from);
        if (savedPinCode.equals(pinCode)) {
            pinCodeCache.remove(from);
            return ResponseEntity.ok("Your email has been confirmed");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Pin code doesn't match");
        }
    }
}
