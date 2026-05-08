package com.bookNDrive.notification_service.controllers;

import com.bookNDrive.notification_service.dtos.received.ForgotPassword;
import com.bookNDrive.notification_service.services.MailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mails")
@Tag(name = "Mail Controller", description = "Expose les operations d'envoi des emails de notification.")
public class MailController {

    private final MailService mailService;

    @Autowired
    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @Operation(
            summary = "Envoyer un email de reinitialisation",
            description = "Declenche l'envoi d'un email de reinitialisation de mot de passe a partir de l'adresse email et du token fournis."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "202",
                    description = "Demande d'envoi acceptee"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Corps de requete invalide"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erreur technique pendant l'envoi du mail"
            )
    })
    @PostMapping("/forgot-password")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void sendMailToResetPassword(@Valid @RequestBody ForgotPassword forgotPassword) {
        mailService.sendForgotPasswordMail(forgotPassword);
    }
}
