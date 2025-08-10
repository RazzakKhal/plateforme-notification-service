package com.bookNDrive.notification_service.records;

import java.util.List;

public record BrevoEmail(Sender sender,
                         List<Recipient> to,
                         String subject,
                         String htmlContent) {}
