package com.delta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication(scanBasePackages = "com.delta")
public class DeltaNotificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeltaNotificationApplication.class, args);
    }

}
