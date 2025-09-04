package com.hms.appointment.events;

import java.time.Instant;
import java.time.LocalDateTime;

//events/AppointmentAcceptedEvent.java
public record AppointmentAcceptedEvent(
 Long appointmentId,
 Long patientId,
 String patientEmail,
 String doctorId,
 LocalDateTime slot,
 String eventType,          // "APPOINTMENT_ACCEPTED"
 Instant occurredAt
) {}
