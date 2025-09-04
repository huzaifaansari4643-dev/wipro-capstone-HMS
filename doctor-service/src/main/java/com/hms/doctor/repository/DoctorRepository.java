package com.hms.doctor.repository;

import com.hms.doctor.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DoctorRepository extends MongoRepository<Doctor, String> {
    Optional<Doctor> findByEmail(String email);
    Page<Doctor> findBySpecialty(String specialty, Pageable pageable);
    Page<Doctor> findByDepartment(String department, Pageable pageable);
}