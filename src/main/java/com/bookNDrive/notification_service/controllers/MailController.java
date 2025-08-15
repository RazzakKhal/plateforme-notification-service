package com.bookNDrive.notification_service.controllers;

import com.bookNDrive.notification_service.dtos.received.ForgotPassword;
import com.bookNDrive.notification_service.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mails")
public class MailController {

    private final MailService mailService;

    @Autowired
    public MailController(MailService mailService){
        this.mailService = mailService;
    }

    @PostMapping("/forgot-password")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void sendMailToResetPassword(@RequestBody ForgotPassword forgotPassword){
        mailService.sendForgotPasswordMail(forgotPassword);
    }
}
