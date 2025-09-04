package com.hms.patient.dto;


import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PatientResponseDto {
    private Long id;
    private String name;
    private String address;
    private LocalDate dob;
    private Double weight;
    private Double height;
    private List<Long> medicalRecordIds;
}
