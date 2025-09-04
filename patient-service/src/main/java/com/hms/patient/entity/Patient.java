package com.hms.patient.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name is required")
    private String name;

    private String address;

    @Past(message = "Date of birth must be in the past")
    private LocalDate dob;

    @Positive(message = "Weight must be positive")
    private Double weight;

    @Positive(message = "Height must be positive")
    private Double height;

    @ElementCollection
    @Column(name = "medical_record_id")
    private List<Long> medicalRecordIds; // References to MongoDB
}