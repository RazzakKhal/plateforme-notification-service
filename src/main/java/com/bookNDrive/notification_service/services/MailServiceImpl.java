package com.bookNDrive.notification_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService{

    private final MailSender mailSender;

    @Autowired
    public MailServiceImpl(MailSender mailSender){
        this.mailSender = mailSender;
    }

    @Override
    public void sendMail (String userMail){
        try{
            // récupérer l'utilisateur
            mailSender.sendForgotPassword(userMail);

        } catch (Exception e) {
            System.out.println("outch : " + e.getMessage());
        }
    }
}
