package com.bookNDrive.notification_service.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.brevo")
public record BrevoProperties(
        String apiKey,
        String plateformeName,
        String plateformeUrl,
        String sender
) {
}
