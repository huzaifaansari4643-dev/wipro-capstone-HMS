package com.hms.patient.controller;

import com.hms.patient.dto.PatientRequestDto;
import com.hms.patient.dto.PatientResponseDto;
import com.hms.patient.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<PatientResponseDto> createPatient(@Valid @RequestBody PatientRequestDto requestDto) {
        return ResponseEntity.ok(patientService.createPatient(requestDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<PatientResponseDto> updatePatient(@PathVariable Long id, @Valid @RequestBody PatientRequestDto requestDto) {
        return ResponseEntity.ok(patientService.updatePatient(id, requestDto));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PATIENT','DOCTOR','ADMIN')")
    public ResponseEntity<PatientResponseDto> getPatient(@PathVariable Long id) {
        Optional<PatientResponseDto> patient = patientService.getPatient(id);
        return patient.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<PatientResponseDto>> getAllPatients(Pageable pageable) {
        return ResponseEntity.ok(patientService.getAllPatients(pageable));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}