package com.bookNDrive.notification_service.configurations;

import java.net.http.HttpClient;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.bookNDrive.notification_service.dtos.received.ForgotPassword;
import com.bookNDrive.notification_service.services.MailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailConfiguration {

    @Bean
    public HttpClient httpClient(){
        return HttpClient.newHttpClient();
    }

    @Bean
    public Consumer<ForgotPassword> sendResetLink(MailService mailService){
        return (forgotPassword) -> {
            mailService.sendForgotPasswordMail(forgotPassword);
        };
    };
}
