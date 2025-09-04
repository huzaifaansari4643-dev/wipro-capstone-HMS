package com.hms.doctor.dto;

import lombok.Data;





@Data
public class AvailabilityDto {
    private String day;
    private String startTime;
    private String endTime;
}