package com.hms.doctor.controller;

import com.hms.doctor.dto.AvailabilityDto;
import com.hms.doctor.dto.DoctorRequestDto;
import com.hms.doctor.dto.DoctorResponseDto;
import com.hms.doctor.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/profile")
    public ResponseEntity<DoctorResponseDto> createOrUpdateProfile(@RequestBody DoctorRequestDto dto,
                                                                   @RequestHeader("X-Auth-User-Email") String email,
                                                                   @RequestHeader("X-Auth-User-Role") String role) {
        return ResponseEntity.ok(doctorService.createOrUpdateProfile(email, role, dto.getName(), dto.getSpecialty(), dto.getDepartment(), dto.getAvailability()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponseDto> getDoctor(@PathVariable String id) {
        return ResponseEntity.ok(doctorService.getDoctorById(id));
    }

    @GetMapping
    public ResponseEntity<Page<DoctorResponseDto>> getAllDoctors(Pageable pageable,
                                                                 @RequestParam(required = false) String specialty,
                                                                 @RequestParam(required = false) String department) {
        return ResponseEntity.ok(doctorService.getAllDoctors(pageable, specialty, department));
    }

    @PostMapping
    public ResponseEntity<DoctorResponseDto> createDoctor(@RequestBody DoctorRequestDto dto,
                                                          @RequestHeader("X-Auth-User-Role") String role) {
        return ResponseEntity.ok(doctorService.createDoctor(role, dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponseDto> updateDoctor(@PathVariable String id,
                                                          @RequestBody DoctorRequestDto dto,
                                                          @RequestHeader("X-Auth-User-Role") String role) {
        return ResponseEntity.ok(doctorService.updateDoctor(id, role, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable String id,
                                             @RequestHeader("X-Auth-User-Role") String role) {
        doctorService.deleteDoctor(id, role);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/schedule")
    public ResponseEntity<List<AvailabilityDto>> getSchedule(@PathVariable String id) {
        return ResponseEntity.ok(doctorService.getSchedule(id));
    }

    @PutMapping("/{id}/schedule")
    public ResponseEntity<Void> updateSchedule(@PathVariable String id,
                                               @RequestBody List<AvailabilityDto> availability,
                                               @RequestHeader("X-Auth-User-Role") String role) {
        doctorService.updateSchedule(id, role, availability);
        return ResponseEntity.ok().build();
    }
}