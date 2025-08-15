package com.bookNDrive.notification_service.services;

import com.bookNDrive.notification_service.dtos.received.ForgotPassword;
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
    public void sendForgotPasswordMail (ForgotPassword forgotPassword){
        try{
            mailSender.sendForgotPassword(forgotPassword);

        } catch (Exception e) {
            System.out.println("outch : " + e.getMessage());
        }
    }

}
