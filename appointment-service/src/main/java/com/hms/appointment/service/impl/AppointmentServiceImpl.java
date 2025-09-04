package com.hms.appointment.service.impl;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hms.appointment.client.DoctorClient;
import com.hms.appointment.client.PatientClient;
import com.hms.appointment.dto.AppointmentRequestDto;
import com.hms.appointment.dto.AppointmentResponseDto;
import com.hms.appointment.entity.Appointment;
import com.hms.appointment.events.AppointmentAcceptedEvent;
import com.hms.appointment.repository.AppointmentRepository;
import com.hms.appointment.service.AppointmentService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;




@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
private final AppointmentRepository repo;
private final PatientClient patients;
private final DoctorClient doctors;
private final KafkaTemplate<String, Object> kafka;

@Transactional
public AppointmentResponseDto create(AppointmentRequestDto req, String callerEmail, String callerRole) {
 // PATIENT only
 if (!"PATIENT".equalsIgnoreCase(callerRole)) throw new AccessDeniedException("Only PATIENT can book");
 // validate patient/doctor exist
 patients.get(req.patientId());  // 404 from service will bubble
 doctors.get(req.doctorId());
 var entity = new Appointment();
 entity.setPatientId(req.patientId());
 entity.setPatientEmail(callerEmail);
 entity.setDoctorId(req.doctorId());
 entity.setSlot(req.slot());
 entity.setNotes(req.notes());
 var saved = repo.save(entity);
 return map(saved);
}

@Transactional
public AppointmentResponseDto accept(Long id, String doctorEmail, String doctorRole) {
 if (!"DOCTOR".equalsIgnoreCase(doctorRole) && !"ADMIN".equalsIgnoreCase(doctorRole))
   throw new AccessDeniedException("Only DOCTOR/ADMIN can accept");
 var appt = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Appointment not found"));
 appt.setStatus(Appointment.Status.ACCEPTED);
 appt.setUpdatedAt(LocalDateTime.now());
 var saved = repo.save(appt);

 // Notify via Kafka (email service consumes)
 var evt = new AppointmentAcceptedEvent(
     saved.getId(), saved.getPatientId(), saved.getPatientEmail(),
     saved.getDoctorId(), saved.getSlot(), "APPOINTMENT_ACCEPTED", Instant.now());
 kafka.send("notification-topic", "appointment-"+saved.getId(), evt);
 // Also ship to audit if you like:
 kafka.send("audit-topic", "appointment-"+saved.getId(), evt);

 return map(saved);
}

public Page<AppointmentResponseDto> forDoctor(String doctorId, Pageable pageable) {
 return repo.findByDoctorIdOrderBySlotDesc(doctorId, pageable).map(this::map);
}

public Page<AppointmentResponseDto> forPatient(Long patientId, Pageable pageable) {
 return repo.findByPatientIdOrderBySlotDesc(patientId, pageable).map(this::map);
}

private AppointmentResponseDto map(Appointment a) {
 return new AppointmentResponseDto(a.getId(), a.getPatientId(), a.getPatientEmail(),
   a.getDoctorId(), a.getSlot(), a.getStatus().name(), a.getNotes());
}
}
