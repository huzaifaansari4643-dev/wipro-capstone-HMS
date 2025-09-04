package com.hms.doctor.repository;


import com.hms.doctor.entity.MedicalRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MedicalRecordRepository extends MongoRepository<MedicalRecord, String> {
}