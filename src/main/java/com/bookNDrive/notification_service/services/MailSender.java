package com.bookNDrive.notification_service.services;

import com.bookNDrive.notification_service.dtos.received.ForgotPassword;

public interface MailSender {

    void sendForgotPassword(ForgotPassword forgotPassword) throws Exception;
}
