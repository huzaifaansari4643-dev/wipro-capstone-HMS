package com.hms.doctor.controller;


import com.hms.doctor.dto.MedicalRecordRequestDto;
import com.hms.doctor.dto.MedicalRecordResponseDto;
import com.hms.doctor.service.MedicalRecordService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/medical-records")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @PostMapping
    public ResponseEntity<MedicalRecordResponseDto> createMedicalRecord(@Valid @RequestBody MedicalRecordRequestDto requestDto) {
        return ResponseEntity.ok(medicalRecordService.createMedicalRecord(requestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalRecordResponseDto> updateMedicalRecord(@PathVariable String id, @Valid @RequestBody MedicalRecordRequestDto requestDto) {
        return ResponseEntity.ok(medicalRecordService.updateMedicalRecord(id, requestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecordResponseDto> getMedicalRecord(@PathVariable String id) {
        return medicalRecordService.getMedicalRecord(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<MedicalRecordResponseDto>> getAllMedicalRecords(Pageable pageable) {
        return ResponseEntity.ok(medicalRecordService.getAllMedicalRecords(pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicalRecord(@PathVariable String id) {
        medicalRecordService.deleteMedicalRecord(id);
        return ResponseEntity.noContent().build();
    }
}