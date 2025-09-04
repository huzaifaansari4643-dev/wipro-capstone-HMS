package com.hms.billing.dto;

import java.math.BigDecimal;

public record BillCreateRequest(
        Long appointmentId,
        Long patientId,
        String patientEmail,
        BigDecimal drugsAmount,
        BigDecimal consultationFee
) {}
