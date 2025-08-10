package com.bookNDrive.notification_service.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mails")
public class MailController {

    @GetMapping("/forgot-password")
    public String test(){
        return "test reussi";
    }
}
