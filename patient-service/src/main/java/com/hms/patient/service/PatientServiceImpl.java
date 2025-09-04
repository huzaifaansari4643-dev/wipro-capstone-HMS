package com.hms.patient.service;


import com.hms.patient.dto.PatientRequestDto;
import com.hms.patient.dto.PatientResponseDto;
import com.hms.patient.entity.Patient;
import com.hms.patient.repository.PatientRepository;

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
public class PatientServiceImpl implements PatientService {

    private static final Logger logger = LoggerFactory.getLogger(PatientServiceImpl.class);

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ModelMapper modelMapper; // Add ModelMapper bean in config if needed

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public PatientResponseDto createPatient(PatientRequestDto requestDto) {
        logger.info("Creating patient: {}", requestDto.getName());
        Patient patient = modelMapper.map(requestDto, Patient.class);
        Patient savedPatient = patientRepository.save(patient);
        kafkaTemplate.send("billing-topic", "Patient created: ID=" + savedPatient.getId());
        return modelMapper.map(savedPatient, PatientResponseDto.class);
    }

    @Override
    public PatientResponseDto updatePatient(Long id, PatientRequestDto requestDto) {
        logger.info("Updating patient ID: {}", id);
        return patientRepository.findById(id)
                .map(patient -> {
                    modelMapper.map(requestDto, patient);
                    Patient updatedPatient = patientRepository.save(patient);
                    kafkaTemplate.send("billing-topic", "Patient updated: ID=" + updatedPatient.getId());
                    return modelMapper.map(updatedPatient, PatientResponseDto.class);
                })
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
    }

    @Override
    public Optional<PatientResponseDto> getPatient(Long id) {
        return patientRepository.findById(id)
                .map(patient -> modelMapper.map(patient, PatientResponseDto.class));
    }

    @Override
    public Page<PatientResponseDto> getAllPatients(Pageable pageable) {
        return patientRepository.findAll(pageable)
                .map(patient -> modelMapper.map(patient, PatientResponseDto.class));
    }

    @Override
    public void deletePatient(Long id) {
        logger.info("Deleting patient ID: {}", id);
        patientRepository.deleteById(id);
        kafkaTemplate.send("billing-topic", "Patient deleted: ID=" + id);
    }
}