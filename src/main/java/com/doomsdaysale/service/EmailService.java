package com.doomsdaysale.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void sendVerificationOtpEmail(String userEmail, String otp, String subject, String text) throws MessagingException {
        try {
            log.debug("Attempting to send email to: {}", userEmail);
            // create a mime message which is used to send the user.
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
            mimeMessageHelper.setFrom("manmohanfeb13@gmail.com");
            mimeMessageHelper.setTo(userEmail);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(text);
            javaMailSender.send(mimeMessage);
            log.info("Email sent successfully to: {}", userEmail);
        } catch (MailException e) {
            log.error("Failed to send email to: {}. Error: {}", userEmail, e.getMessage(), e);
            throw new MailSendException("Failed to send email: " + e.getMessage(), e);
        }
    }
}
