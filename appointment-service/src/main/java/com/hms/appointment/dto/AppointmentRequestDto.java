package com.hms.appointment.dto;



import java.time.LocalDateTime;

//AppointmentRequestDto.java
public record AppointmentRequestDto(
 Long patientId,
 String doctorId,
 LocalDateTime slot,
 String notes
) {}
