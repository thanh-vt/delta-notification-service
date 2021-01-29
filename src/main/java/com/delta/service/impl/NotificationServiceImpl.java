package com.delta.service.impl;

import com.delta.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final JavaMailSender mailSender;

    private final Environment environment;

    @Autowired
    public NotificationServiceImpl(JavaMailSender mailSender,
        Environment environment) {
        this.mailSender = mailSender;
        this.environment = environment;
    }

}
