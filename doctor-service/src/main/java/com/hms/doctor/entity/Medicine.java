package com.hms.doctor.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medicine {
    @NotNull(message = "Medicine name is required")
    private String name;

    @NotNull(message = "Dosage time is required")
    private DosageTime dosageTime;

    @Positive(message = "Quantity must be positive")
    private Integer quantity;
}