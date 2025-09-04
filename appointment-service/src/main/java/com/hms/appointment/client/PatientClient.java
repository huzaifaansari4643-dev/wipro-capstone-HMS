package com.hms.appointment.client;

import com.hms.appointment.config.FeignConfig;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PATIENT-SERVICE", configuration = FeignConfig.class)
public interface PatientClient {
  @GetMapping("/api/patients/{id}")
  ResponseEntity<PatientDto> get(@PathVariable Long id);
  record PatientDto(Long id, String name, String address) {}
}