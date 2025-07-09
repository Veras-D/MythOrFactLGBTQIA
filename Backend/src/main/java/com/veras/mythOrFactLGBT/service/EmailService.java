package com.veras.mythOrFactLGBT.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${spring.mail.properties.mail.smtp.from-name}")
    private String fromName;

    @Value("${myth.or.fact.lgbt.backend.url}")
    private String backendBaseUrl;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendConfirmationEmail(String to, String token) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail, fromName);
            helper.setTo(to);
            helper.setSubject("Confirm your signup for MythOrFactLGBT+");

            String htmlContent = """
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <title>Confirm your signup</title>
    <style>
      body {
        background-color: #fff8f9;
        font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
        color: #333;
        text-align: center;
        padding: 2rem;
      }
      .card {
        background-color: white;
        border-radius: 12px;
        box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
        padding: 2rem;
        max-width: 500px;
        margin: 0 auto;
      }
      .highlight {
        font-weight: bold;
        color: #00c37a;
      }
      a.button {
        display: inline-block;
        background-color: #00c37a;
        color: white;
        padding: 12px 24px;
        border-radius: 8px;
        text-decoration: none;
        font-weight: bold;
        margin-top: 1.5rem;
        transition: background-color 0.3s;
      }
      a.button:hover {
        background-color: #00a265;
      }
      .footer {
        font-size: 0.9rem;
        margin-top: 2rem;
        color: #999;
      }
    </style>
  </head>
  <body>
    <div class="card">
      <h2>🌈 Confirm your signup!</h2>
      <p>
        You're just one click away from joining our colorful world of
        <span class="highlight">truths and fun facts</span> 🌟
      </p>
      <p>Click the button below to confirm your email:</p>
      <a href="%s/api/auth/confirm?token=%s" class="button">✅ Confirm my email</a>
    </div>
    <p class="footer">
      If you did not request this, you can safely ignore this email.
    </p>
  </body>
</html>
""".formatted(backendBaseUrl, token);

            helper.setText(htmlContent, true);
            mailSender.send(message);

        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Failed to send confirmation email", e);
        }
    }

    public void sendPasswordResetEmail(String to, String resetToken) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail, fromName);
            helper.setTo(to);
            helper.setSubject("Password Reset Request for MythOrFactLGBT+");

            String htmlContent = """
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <title>Password Reset</title>
    <style>
      body {
        background-color: #fff8f9;
        font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
        color: #333;
        text-align: center;
        padding: 2rem;
      }
      .card {
        background-color: white;
        border-radius: 12px;
        box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
        padding: 2rem;
        max-width: 500px;
        margin: 0 auto;
      }
      .highlight {
        font-weight: bold;
        color: #00c37a;
      }
      a.button {
        display: inline-block;
        background-color: #00c37a;
        color: white;
        padding: 12px 24px;
        border-radius: 8px;
        text-decoration: none;
        font-weight: bold;
        margin-top: 1.5rem;
        transition: background-color 0.3s;
      }
      a.button:hover {
        background-color: #00a265;
      }
      .footer {
        font-size: 0.9rem;
        margin-top: 2rem;
        color: #999;
      }
    </style>
  </head>
  <body>
    <div class="card">
      <h2>Password Reset Request</h2>
      <p>
        You have requested to reset your password for your MythOrFactLGBT+ account.
      </p>
      <p>Click the button below to reset your password:</p>
      <a href="%s/reset-password?token=%s" class="button">🔑 Reset My Password</a>
    </div>
    <p class="footer">
      If you did not request a password reset, please ignore this email.
    </p>
  </body>
</html>
""".formatted(backendBaseUrl, resetToken);

            helper.setText(htmlContent, true);
            mailSender.send(message);

        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Failed to send password reset email", e);
        }
    }
}