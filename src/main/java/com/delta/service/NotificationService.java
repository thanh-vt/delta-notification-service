package com.delta.service;

import com.delta.model.ws.EmailAuthenticationRequest;
import com.delta.model.ws.SendEmailRequest;
import com.delta.model.ws.SendNotificationRequest;
import com.delta.model.ws.SendSMSRequest;
import java.io.IOException;
import javax.mail.MessagingException;

public interface NotificationService {

    String emailAuthentication(EmailAuthenticationRequest request);

    void sendNotification(SendNotificationRequest request);

    void sendSMS(SendSMSRequest request);

    void sendEmail(SendEmailRequest request) throws IOException, MessagingException;

}
