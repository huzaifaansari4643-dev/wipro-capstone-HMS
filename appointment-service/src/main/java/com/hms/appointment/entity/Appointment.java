package com.hms.appointment.entity;


import jakarta.persistence.*;



import lombok.Getter;

import lombok.Setter;

import java.time.LocalDateTime;

//Appointment.java
@Entity
@Table(name = "appointments")
@Getter @Setter
public class Appointment {
@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@Column(nullable=false) private Long patientId;
@Column(nullable=false) private String patientEmail;
@Column(nullable=false) private String doctorId;   // Doctor service uses String IDs
@Column(nullable=false) private LocalDateTime slot;

@Enumerated(EnumType.STRING)
@Column(nullable=false) private Status status = Status.PENDING;

private String notes;         // reason / symptoms
private LocalDateTime createdAt = LocalDateTime.now();
private LocalDateTime updatedAt = LocalDateTime.now();

public enum Status { PENDING, ACCEPTED, REJECTED, CANCELLED, COMPLETED }
}
