package com.delta.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
public class CustomResourceConfig {

    @Bean
    public Resource notificationResource() {
        return new ClassPathResource("notification.xsd");
    }
}
