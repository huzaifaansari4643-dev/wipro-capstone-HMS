package com.hms.appointment.dto;


import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PatientResponseDto {
    private Long id;
    private String name;
    private String address;
    private LocalDate dob; // Date of Birth
    private Double weight;
    private Double height;
    private List<String> medicalRecordIds; // Assuming a list of IDs or similar
}