package com.hms.appointment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hms.appointment.config.FeignConfig;


@FeignClient(name = "DOCTOR-SERVICE", configuration = FeignConfig.class)
public interface DoctorClient {
  @GetMapping("/api/doctors/{id}")
  ResponseEntity<DoctorDto> get(@PathVariable String id);
  record DoctorDto(String id, String name, String specialty, String department) {}
}