package com.bookNDrive.notification_service.services;

public interface MailSender {

    void sendForgotPassword(String recipient) throws Exception;
}
