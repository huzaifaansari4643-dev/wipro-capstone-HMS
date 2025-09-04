package com.hms.doctor.entity;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "medical_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalRecord {
    @Id
    private String id;

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
    private List<Medicine> medicines;
}