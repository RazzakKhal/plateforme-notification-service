package com.bookNDrive.notification_service.services;

import com.bookNDrive.notification_service.dtos.received.ForgotPassword;
import com.bookNDrive.notification_service.records.BrevoEmail;
import com.bookNDrive.notification_service.records.Recipient;
import com.bookNDrive.notification_service.records.Sender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Component
public class Brevo implements MailSender{

    private final HttpClient httpClient;


    @Autowired
    public Brevo(HttpClient httpClient) throws JsonProcessingException {
        this.httpClient = httpClient;
    }

    @Value("${app.brevo.api-key}") String apiKey;
    private String sender = "secretariat@ask-autoecole.fr";
    private String plateformeName = "ASK Plateforme";
    private String plateformeUrl = "https://ask-plateforme.fr/reset-password/";


    @Override
    public void sendForgotPassword(ForgotPassword forgotPassword) throws Exception {

        var subject = "Reinitialisation Mot de passe";
        var message = String.format("<html><body><h1>Bonjour</h1><p>Votre lien pour réinitialiser votre mot de passe : %s%s</p></body></html>", plateformeUrl,forgotPassword.token());

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

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Code HTTP: " + response.statusCode());
        System.out.println("Réponse: " + response.body());
    }
}
