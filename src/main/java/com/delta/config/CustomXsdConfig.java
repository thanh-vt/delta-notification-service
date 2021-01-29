package com.delta.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@Configuration
public class CustomXsdConfig {

    @Bean
    public XsdSchema notificationSchema(Resource notificationResource) {
        return new SimpleXsdSchema(notificationResource);
    }
}
