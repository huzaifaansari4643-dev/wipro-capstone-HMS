package com.hms.doctor.service;

import com.hms.doctor.dto.AvailabilityDto;
import com.hms.doctor.dto.DoctorRequestDto;
import com.hms.doctor.dto.DoctorResponseDto;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DoctorService {
    DoctorResponseDto createOrUpdateProfile(String email, String role, String name, String specialty, String department, List<AvailabilityDto> availability);
    DoctorResponseDto getDoctorById(String id);
    Page<DoctorResponseDto> getAllDoctors(Pageable pageable, String specialty, String department);
    DoctorResponseDto createDoctor(String role, DoctorRequestDto dto);
    DoctorResponseDto updateDoctor(String id, String role, DoctorRequestDto dto);
    void deleteDoctor(String id, String role);
    List<AvailabilityDto> getSchedule(String id);
    void updateSchedule(String id, String role, List<AvailabilityDto> availability);
}