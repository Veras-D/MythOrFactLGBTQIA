package com.veras.mythOrFactLGBT.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${myth.or.fact.lgbt.backend.url}")
    private String backendBaseUrl;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendConfirmationEmail(String to, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject("Email Confirmation for MythOrFactLGBT+");
        message.setText("Thank you for registering. Please click on the following link to activate your account: "
                + backendBaseUrl + "/api/auth/confirm?token=" + token);
        mailSender.send(message);
    }
}