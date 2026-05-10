package com.bookNDrive.notification_service.services;

import com.bookNDrive.notification_service.dtos.received.ForgotPassword;
import com.bookNDrive.notification_service.exceptions.NotificationSendFailedException;
import com.bookNDrive.notification_service.records.BrevoEmail;
import com.bookNDrive.notification_service.records.Recipient;
import com.bookNDrive.notification_service.records.Sender;
import com.bookndrive.common.util.SensitiveDataMasker;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Component
public class Brevo implements MailSender {

    private static final Logger log = LoggerFactory.getLogger(Brevo.class);

    private final HttpClient httpClient;

    @Value("${app.brevo.api-key}")
    String apiKey;

    private final String sender = "secretariat@ask-autoecole.fr";
    private final String plateformeName = "ASK Plateforme";
    private final String plateformeUrl = "https://ask-plateforme.fr/reset-password/";

    @Autowired
    public Brevo(HttpClient httpClient) throws JsonProcessingException {
        this.httpClient = httpClient;
    }

    @Override
    public void sendForgotPassword(ForgotPassword forgotPassword) throws Exception {
        String maskedMail = SensitiveDataMasker.maskEmail(forgotPassword.mail());
        var subject = "Reinitialisation Mot de passe";
        var message = String.format(
                "<html><body><h1>Bonjour</h1><p>Votre lien pour reinitialiser votre mot de passe : %s%s</p></body></html>",
                plateformeUrl,
                forgotPassword.token()
        );

        BrevoEmail email = new BrevoEmail(
                new Sender(plateformeName, sender),
                List.of(new Recipient(forgotPassword.mail(), "Destinataire")),
                subject,
                message
        );

        ObjectMapper mapper = new ObjectMapper();
        String jsonBody = mapper.writeValueAsString(email);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.brevo.com/v3/smtp/email"))
                .header("accept", "application/json")
                .header("api-key", apiKey)
                .header("content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        log.info("Appel au fournisseur d'email initie provider=brevo mail={}", maskedMail);
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 400) {
            log.warn(
                    "Le fournisseur d'email a refuse l'envoi provider=brevo mail={} statusCode={}",
                    maskedMail,
                    response.statusCode()
            );
            throw new NotificationSendFailedException("Le fournisseur email a refuse la demande d'envoi");
        }
        log.info(
                "Le fournisseur d'email a accepte l'envoi provider=brevo mail={} statusCode={}",
                maskedMail,
                response.statusCode()
        );
    }
}
