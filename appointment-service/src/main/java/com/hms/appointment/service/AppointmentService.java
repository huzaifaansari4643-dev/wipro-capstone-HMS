package com.hms.appointment.service;

import com.hms.appointment.dto.AppointmentRequestDto;
import com.hms.appointment.dto.AppointmentResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



//AppointmentService.java
public interface AppointmentService {
AppointmentResponseDto create(AppointmentRequestDto req, String callerEmail, String callerRole);
AppointmentResponseDto accept(Long id, String doctorEmail, String doctorRole);
Page<AppointmentResponseDto> forDoctor(String doctorId, Pageable pageable);
Page<AppointmentResponseDto> forPatient(Long patientId, Pageable pageable);
}
