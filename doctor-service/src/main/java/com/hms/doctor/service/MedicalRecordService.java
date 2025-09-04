package com.hms.doctor.service;

import com.hms.doctor.dto.MedicalRecordRequestDto;
import com.hms.doctor.dto.MedicalRecordResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MedicalRecordService {
    MedicalRecordResponseDto createMedicalRecord(MedicalRecordRequestDto requestDto);
    MedicalRecordResponseDto updateMedicalRecord(String id, MedicalRecordRequestDto requestDto);
    Optional<MedicalRecordResponseDto> getMedicalRecord(String id);
    Page<MedicalRecordResponseDto> getAllMedicalRecords(Pageable pageable);
    void deleteMedicalRecord(String id);
}