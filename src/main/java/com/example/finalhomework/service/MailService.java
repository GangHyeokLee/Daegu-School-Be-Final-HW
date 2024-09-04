package com.example.finalhomework.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Value("${NAVER_USERNAME}")
    private String sendMail;
    private final JavaMailSenderImpl mailSender;

    public MailService(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }

    public void create(String title, String content, String addr) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sendMail);
        message.setTo(addr);
        message.setSubject(title);
        message.setText(content);

        mailSender.send(message);
    }
}
