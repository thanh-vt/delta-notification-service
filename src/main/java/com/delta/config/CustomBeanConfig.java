package com.delta.config;

import com.delta.constant.WebServiceConstant;
import com.delta.model.ws.ObjectFactory;
import javax.servlet.http.HttpServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.ws.wsdl.wsdl11.Wsdl11Definition;
import org.springframework.xml.xsd.XsdSchema;

@Configuration
public class CustomBeanConfig {

    @Bean(name = "notification")
    public Wsdl11Definition usersWsdl11Definition(XsdSchema usersSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("NotificationPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace(WebServiceConstant.NOTIFICATION_NAMESPACE);
        wsdl11Definition.setServiceName("NotificationService");
        wsdl11Definition.setResponseSuffix("Response");
        wsdl11Definition.setRequestSuffix("Request");
        wsdl11Definition.setFaultSuffix("Fault");
        wsdl11Definition.setSchema(usersSchema);
        return wsdl11Definition;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Primary
    public ServletRegistrationBean<HttpServlet> messageDispatcherServlet(
        ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    @Bean(name = "objectFactory")
    public ObjectFactory objectFactory() {
        return new ObjectFactory();
    }
}
