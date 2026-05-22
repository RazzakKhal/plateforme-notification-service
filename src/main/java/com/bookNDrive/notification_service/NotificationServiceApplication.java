package com.bookNDrive.notification_service;

import com.bookNDrive.notification_service.properties.BrevoProperties;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Notification Service",
                description = "Microservice responsable des Notifications par mail, sms, etc"
        )
)
@EnableConfigurationProperties({BrevoProperties.class})
public class NotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

}
