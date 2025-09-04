package com.hms.doctor.dto;



import java.util.List;

import lombok.Data;

@Data
public class DoctorRequestDto {
    private String name;
    private String email;
    private String specialty;
    private String department;
    private List<AvailabilityDto> availability;
}