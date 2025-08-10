package com.bookNDrive.notification_service.controllers;

import com.bookNDrive.notification_service.dtos.received.ForgotPassword;
import com.bookNDrive.notification_service.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mails")
public class MailController {

    private final MailService mailService;

    @Autowired
    public MailController(MailService mailService){
        this.mailService = mailService;
    }

    @PostMapping("/forgot-password")
    public String sendMailToResetPassword(@RequestBody ForgotPassword forgotPassword){
        // récupère l'utilisateur en bdd via le mail (openfeign)
        // si il existe alors envoi le mail
        // sinon retourne utilisateur inconnu
        mailService.sendMail(forgotPassword.mail());

        return "test reussi";
    }
}
