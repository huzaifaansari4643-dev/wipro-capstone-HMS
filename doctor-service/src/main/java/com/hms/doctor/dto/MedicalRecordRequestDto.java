package com.hms.doctor.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class MedicalRecordRequestDto {
    @NotNull(message = "Patient ID is required")
    private Long patientId;

    @NotNull(message = "Description is required")
    private String description;

    @NotNull(message = "Doctor is required")
    private String doctor;

    @NotNull(message = "Treated at time is required")
    private LocalDateTime treatedAt;

    private LocalDate revisitingDate;

    @NotNull(message = "Medicines list cannot be empty")
    private List<MedicineDto> medicines;
}

@Data
class MedicineDto {
    @NotNull(message = "Medicine name is required")
    private String name;

    @NotNull(message = "Dosage time is required")
    private String dosageTime; // Map to DosageTime enum later if needed

    @NotNull(message = "Quantity is required")
    private Integer quantity;
}