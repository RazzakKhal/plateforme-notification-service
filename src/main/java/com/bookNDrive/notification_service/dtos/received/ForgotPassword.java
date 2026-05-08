package com.bookNDrive.notification_service.dtos.received;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ForgotPassword(
        @NotBlank(message = "must not be blank")
        @Email(message = "must be a valid email")
        String mail,
        @NotBlank(message = "must not be blank")
        String token
) {
}
