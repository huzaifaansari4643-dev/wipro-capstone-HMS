package com.hms.patient.service;



import com.hms.patient.dto.PatientRequestDto;
import com.hms.patient.dto.PatientResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PatientService {
    PatientResponseDto createPatient(PatientRequestDto requestDto);
    PatientResponseDto updatePatient(Long id, PatientRequestDto requestDto);
    Optional<PatientResponseDto> getPatient(Long id);
    Page<PatientResponseDto> getAllPatients(Pageable pageable);
    void deletePatient(Long id);
}