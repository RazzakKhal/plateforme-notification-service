package com.bookNDrive.notification_service.services;

import com.bookNDrive.notification_service.dtos.received.ForgotPassword;
import com.bookNDrive.notification_service.exceptions.NotificationSendFailedException;
import com.bookndrive.common.util.SensitiveDataMasker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    private static final Logger log = LoggerFactory.getLogger(MailServiceImpl.class);

    private final MailSender mailSender;

    @Autowired
    public MailServiceImpl(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendForgotPasswordMail(ForgotPassword forgotPassword) {
        String maskedMail = SensitiveDataMasker.maskEmail(forgotPassword.mail());
        log.info("Envoi d'un email de reinitialisation demande mail={}", maskedMail);
        try {
            mailSender.sendForgotPassword(forgotPassword);
            log.info("Email de reinitialisation envoye mail={}", maskedMail);
        } catch (NotificationSendFailedException ex) {
            log.warn("Echec de l'envoi de l'email de reinitialisation mail={}", maskedMail);
            throw ex;
        } catch (Exception ex) {
            log.error("Erreur inattendue pendant l'envoi de l'email de reinitialisation mail={}", maskedMail, ex);
            throw new NotificationSendFailedException("L'envoi de l'email a echoue");
        }
    }
}
