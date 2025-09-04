package com.hms.doctor.entity;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class Availability {
    private String day; // e.g., "Monday"
    private String startTime; // e.g., "09:00"
    private String endTime; // e.g., "17:00"
}