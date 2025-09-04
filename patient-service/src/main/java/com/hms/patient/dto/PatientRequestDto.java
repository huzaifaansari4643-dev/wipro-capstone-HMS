package com.hms.patient.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PatientRequestDto {
    @NotNull(message = "Name is required")
    private String name;

    private String address;

    @Past(message = "Date of birth must be in the past")
    private LocalDate dob;

    @Positive(message = "Weight must be positive")
    private Double weight;

    @Positive(message = "Height must be positive")
    private Double height;

    private List<Long> medicalRecordIds;
}