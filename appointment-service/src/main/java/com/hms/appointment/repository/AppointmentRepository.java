package com.hms.appointment.repository;

import com.hms.appointment.entity.Appointment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

//AppointmentRepository.java
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
Page<Appointment> findByDoctorIdOrderBySlotDesc(String doctorId, Pageable pageable);
Page<Appointment> findByPatientIdOrderBySlotDesc(Long patientId, Pageable pageable);
}
