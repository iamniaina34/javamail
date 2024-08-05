package com.javatech.javamail.services;

import com.javatech.javamail.models.PasswordResetToken;
import com.javatech.javamail.models.User;
import com.javatech.javamail.repositories.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordService {
    @Autowired
    private final PasswordResetTokenRepository tokenRepository;
    @Autowired
    private final UserService userService;

    public PasswordService(PasswordResetTokenRepository tokenRepository, UserService userService) {
        this.tokenRepository = tokenRepository;
        this.userService = userService;
    }

    public String createPasswordResetToken (User user) {
        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken(token, user);
        tokenRepository.save(resetToken);
        return token;
    }

    public boolean validateResetPasswordToken(String token) {
        Optional<PasswordResetToken> resetToken = tokenRepository.findByToken(token);
        if (resetToken.isPresent()) {
            PasswordResetToken passwordResetToken = resetToken.get();
            return !passwordResetToken.isExpired();
        } else {
            return false;
        }
    }

    public User getUserByResetToken(String token) {
        Optional<PasswordResetToken> resetToken = tokenRepository.findByToken(token);
        return resetToken.map(PasswordResetToken::getUser).orElse(null);
    }
    public void updateUserPassword(User user, String newPassword) {
        user.setPassword(newPassword); // Ensure the password is encoded properly
        userService.save(user);
    }
}
