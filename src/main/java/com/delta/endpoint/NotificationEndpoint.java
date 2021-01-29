package com.delta.endpoint;

import com.delta.constant.WebServiceConstant;
import com.delta.model.ws.EmailAuthenticationRequest;
import com.delta.model.ws.ObjectFactory;
import com.delta.model.ws.Response;
import com.delta.service.NotificationService;
import javax.xml.bind.JAXBElement;
import org.springframework.beans.factory.annotation.Autowired;
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
        response.setCode(0);
        response.setMessage("Success");
        return this.objectFactory.createEmailAuthenticationResponse(response);
    }
}
