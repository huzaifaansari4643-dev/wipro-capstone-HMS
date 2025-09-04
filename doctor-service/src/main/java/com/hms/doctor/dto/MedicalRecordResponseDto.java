package com.hms.doctor.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class MedicalRecordResponseDto {
    private String id;
    private Long patientId;
    private String description;
    private String doctor;
    private LocalDateTime treatedAt;
    private LocalDate revisitingDate;
    private List<MedicineDto> medicines;
}