package com.hms.doctor.dto;

import lombok.Data;

import java.util.List;

@Data
public class DoctorResponseDto {
    private String id;
    private String name;
    private String email;
    private String specialty;
    private String department;
    private List<AvailabilityDto> availability;
}