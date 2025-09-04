package com.hms.appointment.dto;


import java.time.LocalDateTime;

public record AppointmentResponseDto(
 Long id, Long patientId, String patientEmail,
 String doctorId, LocalDateTime slot,
 String status, String notes
) {}
