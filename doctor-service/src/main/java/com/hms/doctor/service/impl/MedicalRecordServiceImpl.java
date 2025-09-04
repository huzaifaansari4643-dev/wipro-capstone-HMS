package com.hms.doctor.service.impl;

import com.hms.doctor.dto.MedicalRecordRequestDto;
import com.hms.doctor.dto.MedicalRecordResponseDto;
import com.hms.doctor.entity.MedicalRecord;
import com.hms.doctor.repository.MedicalRecordRepository;
import com.hms.doctor.service.MedicalRecordService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private static final Logger logger = LoggerFactory.getLogger(MedicalRecordServiceImpl.class);

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public MedicalRecordResponseDto createMedicalRecord(MedicalRecordRequestDto requestDto) {
        logger.info("Creating medical record for patient ID: {}", requestDto.getPatientId());
        MedicalRecord medicalRecord = modelMapper.map(requestDto, MedicalRecord.class);
        MedicalRecord savedRecord = medicalRecordRepository.save(medicalRecord);
        kafkaTemplate.send("medical-topic", "Medical record created: ID=" + savedRecord.getId());
        return modelMapper.map(savedRecord, MedicalRecordResponseDto.class);
    }

    @Override
    public MedicalRecordResponseDto updateMedicalRecord(String id, MedicalRecordRequestDto requestDto) {
        logger.info("Updating medical record ID: {}", id);
        return medicalRecordRepository.findById(id)
                .map(record -> {
                    modelMapper.map(requestDto, record);
                    MedicalRecord updatedRecord = medicalRecordRepository.save(record);
                    kafkaTemplate.send("medical-topic", "Medical record updated: ID=" + updatedRecord.getId());
                    return modelMapper.map(updatedRecord, MedicalRecordResponseDto.class);
                })
                .orElseThrow(() -> new RuntimeException("Medical record not found with id: " + id));
    }

    @Override
    public Optional<MedicalRecordResponseDto> getMedicalRecord(String id) {
        return medicalRecordRepository.findById(id)
                .map(record -> modelMapper.map(record, MedicalRecordResponseDto.class));
    }

    @Override
    public Page<MedicalRecordResponseDto> getAllMedicalRecords(Pageable pageable) {
        return medicalRecordRepository.findAll(pageable)
                .map(record -> modelMapper.map(record, MedicalRecordResponseDto.class));
    }

    @Override
    public void deleteMedicalRecord(String id) {
        logger.info("Deleting medical record ID: {}", id);
        medicalRecordRepository.deleteById(id);
        kafkaTemplate.send("medical-topic", "Medical record deleted: ID=" + id);
    }
}