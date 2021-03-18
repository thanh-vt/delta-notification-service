package com.delta.service.impl;

import com.delta.constant.CommonConstant;
import com.delta.model.ws.EmailAttachment;
import com.delta.model.ws.EmailAuthenticationRequest;
import com.delta.model.ws.SendEmailRequest;
import com.delta.model.ws.SendNotificationRequest;
import com.delta.model.ws.SendSMSRequest;
import com.delta.service.NotificationService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final Logger logger = LogManager.getLogger(NotificationServiceImpl.class);

    @Value("${folder.temporary}")
    private String tempFolder;

    private final JavaMailSender mailSender;

    @Autowired
    public NotificationServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public String emailAuthentication(EmailAuthenticationRequest request) {
        String recipientAddress = request.getEmail();
        String token = UUID.randomUUID().toString();
        String confirmationUrl = request.getRedirectUrl() + "?token=" + token;
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom(CommonConstant.APP_CODE);
        email.setTo(recipientAddress);
        email.setSentDate(new Date());
        email.setSubject(request.getSubject());
        email.setText(request.getDescription() + "\n" + confirmationUrl);
        this.mailSender.send(email);
        return token;
    }

    @Override
    public void sendNotification(SendNotificationRequest request) {

    }

    @Override
    public void sendSMS(SendSMSRequest request) {

    }

    @Override
    public void sendEmail(SendEmailRequest request) throws IOException, MessagingException {
        EmailAttachment emailAttachment = request.getAttachment();
        File tmpFile = new File(this.tempFolder + emailAttachment.getFileName());
        try (FileOutputStream tmpFOut = new FileOutputStream(tmpFile)) {
            emailAttachment.getFile().writeTo(tmpFOut);
            MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setSentDate(new Date());
            helper.setTo(new InternetAddress(request.getEmail()));
            helper.setFrom(new InternetAddress(CommonConstant.APP_CODE));
            helper.setSubject(request.getSubject());
            String text = request.getHeader() + "\n\n"
                + "============================================================"
                +  "\n\n" + request.getBody() + "\n\n"
                + "============================================================"
                +  "\n\n" + request.getFooter() + "\n\n"
                +  "\n\n" +request.getSignature();
            helper.setText(text);
            FileSystemResource tmpFSResource = new FileSystemResource(tmpFile);
            helper.addAttachment(emailAttachment.getFileName(), tmpFSResource);
            this.mailSender.send(mimeMessage);
        } catch (IOException | MessagingException ex) {
            logger.error(ex);
            throw ex;
        }
    }
}
