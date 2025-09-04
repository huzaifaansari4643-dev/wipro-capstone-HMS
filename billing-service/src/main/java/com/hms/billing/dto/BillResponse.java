package com.hms.billing.dto;

import java.math.BigDecimal;

public record BillResponse(
        Long id,
        Long appointmentId,
        Long patientId,
        String patientEmail,
        BigDecimal drugsAmount,
        BigDecimal consultationFee,
        BigDecimal total,
        String status
) {}
