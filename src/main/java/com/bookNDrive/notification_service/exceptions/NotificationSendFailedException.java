package com.bookNDrive.notification_service.exceptions;

import com.bookndrive.common.error.ApiException;
import org.springframework.http.HttpStatus;

public class NotificationSendFailedException extends ApiException {

    public NotificationSendFailedException(String message) {
        super(message, NotificationErrorCodes.NOTIFICATION_SEND_FAILED, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
