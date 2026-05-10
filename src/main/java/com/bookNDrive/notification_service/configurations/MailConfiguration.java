package com.bookNDrive.notification_service.configurations;

import com.bookNDrive.notification_service.dtos.received.ForgotPassword;
import com.bookNDrive.notification_service.services.MailService;
import com.bookndrive.common.util.SensitiveDataMasker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;
import java.util.function.Consumer;

@Configuration
public class MailConfiguration {

    private static final Logger log = LoggerFactory.getLogger(MailConfiguration.class);

    @Bean
    public HttpClient httpClient() {
        return HttpClient.newHttpClient();
    }

    @Bean
    public Consumer<ForgotPassword> sendResetLink(MailService mailService) {
        return forgotPassword -> {
            log.info(
                    "Message Kafka recu pour l'envoi de reinitialisation mail={}",
                    SensitiveDataMasker.maskEmail(forgotPassword.mail())
            );
            mailService.sendForgotPasswordMail(forgotPassword);
        };
    }
}
