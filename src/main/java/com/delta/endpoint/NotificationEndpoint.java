package com.delta.endpoint;

import com.delta.constant.WebServiceConstant;
import com.delta.model.ws.EmailAuthenticationRequest;
import com.delta.model.ws.ObjectFactory;
import com.delta.model.ws.Response;
import com.delta.model.ws.SendEmailRequest;
import com.delta.model.ws.SendNotificationRequest;
import com.delta.model.ws.SendSMSRequest;
import com.delta.service.NotificationService;
import java.io.IOException;
import javax.mail.MessagingException;
import javax.xml.bind.JAXBElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class NotificationEndpoint {

    private final ObjectFactory objectFactory;

    private final NotificationService notificationService;

    @Autowired
    public NotificationEndpoint(ObjectFactory objectFactory,
        NotificationService notificationService) {
        this.objectFactory = objectFactory;
        this.notificationService = notificationService;
    }

    @PayloadRoot(namespace = WebServiceConstant.NOTIFICATION_NAMESPACE, localPart = "emailAuthenticationRequest")
    @ResponsePayload
    public JAXBElement<Response> authenticateViaEmail(@RequestPayload EmailAuthenticationRequest request) {
        Response response = this.objectFactory.createResponse();
        try {
            String token = this.notificationService.emailAuthentication(request);
            response.setCode(0);
            response.setDescription(token);
        } catch (MailException ex) {
            response.setCode(-1);
            response.setDescription(String.format("Failed: %s", ex.getMessage()));
        }
        return this.objectFactory.createEmailAuthenticationResponse(response);
    }

    @PayloadRoot(namespace = WebServiceConstant.NOTIFICATION_NAMESPACE, localPart = "sendNotificationRequest")
    @ResponsePayload
    public JAXBElement<Response> sendNotification(@RequestPayload SendNotificationRequest request) {
        Response response = this.objectFactory.createResponse();
        try {
            this.notificationService.sendNotification(request);
            response.setCode(0);
            response.setDescription("Success");
        } catch (MailException ex) {
            response.setCode(-1);
            response.setDescription(String.format("Failed: %s", ex.getMessage()));
        }
        return this.objectFactory.createEmailAuthenticationResponse(response);
    }

    @PayloadRoot(namespace = WebServiceConstant.NOTIFICATION_NAMESPACE, localPart = "sendSMSRequest")
    @ResponsePayload
    public JAXBElement<Response> sendSMS(@RequestPayload SendSMSRequest request) {
        Response response = this.objectFactory.createResponse();
        try {
            this.notificationService.sendSMS(request);
            response.setCode(0);
            response.setDescription("Success");
        } catch (MailException ex) {
            response.setCode(-1);
            response.setDescription(String.format("Failed: %s", ex.getMessage()));
        }
        return this.objectFactory.createEmailAuthenticationResponse(response);
    }

    @PayloadRoot(namespace = WebServiceConstant.NOTIFICATION_NAMESPACE, localPart = "sendEmailRequest")
    @ResponsePayload
    public JAXBElement<Response> sendEmail(@RequestPayload SendEmailRequest request) {
        Response response = this.objectFactory.createResponse();
        try {
            this.notificationService.sendEmail(request);
            response.setCode(0);
            response.setDescription("Success");
        } catch (MessagingException | IOException ex) {
            response.setCode(-1);
            response.setDescription(String.format("Failed: %s", ex.getMessage()));
        }
        return this.objectFactory.createEmailAuthenticationResponse(response);
    }
}
