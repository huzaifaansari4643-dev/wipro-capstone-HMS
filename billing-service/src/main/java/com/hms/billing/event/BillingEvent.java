package com.hms.billing.event;

import java.math.BigDecimal;
import java.time.Instant;

public record BillingEvent(
        Long billId,
        Long appointmentId,
        Long patientId,
        String patientEmail,
        BigDecimal total,
        String status,
        String eventType,
        Instant occurredAt
) {}
