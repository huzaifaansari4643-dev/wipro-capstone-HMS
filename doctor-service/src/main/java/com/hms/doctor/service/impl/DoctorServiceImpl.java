package com.hms.doctor.service.impl;

import com.hms.doctor.dto.AvailabilityDto;
import com.hms.doctor.dto.DoctorRequestDto;
import com.hms.doctor.dto.DoctorResponseDto;
import com.hms.doctor.entity.Availability;
import com.hms.doctor.entity.Doctor;
import com.hms.doctor.repository.DoctorRepository;
import com.hms.doctor.service.DoctorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public DoctorResponseDto createOrUpdateProfile(String email, String role, String name, String specialty, String department, List<AvailabilityDto> availability) {
        if (!"DOCTOR".equals(role)) {
            throw new RuntimeException("Unauthorized: Only DOCTOR role can update own profile");
        }

        Optional<Doctor> opt = doctorRepository.findByEmail(email);
        Doctor doctor = opt.orElseGet(Doctor::new);
        doctor.setEmail(email);
        doctor.setName(name);
        doctor.setSpecialty(specialty);
        doctor.setDepartment(department);
        doctor.setAvailability(mapAvailabilityDtosToEntities(availability));
        Doctor saved = doctorRepository.save(doctor);
        return mapToResponse(saved);
    }

    @Override
    public DoctorResponseDto getDoctorById(String id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        return mapToResponse(doctor);
    }

    @Override
    public Page<DoctorResponseDto> getAllDoctors(Pageable pageable, String specialty, String department) {
        Page<Doctor> page;
        if (specialty != null) {
            page = doctorRepository.findBySpecialty(specialty, pageable);
        } else if (department != null) {
            page = doctorRepository.findByDepartment(department, pageable);
        } else {
            page = doctorRepository.findAll(pageable);
        }
        return page.map(this::mapToResponse);
    }

    @Override
    public DoctorResponseDto createDoctor(String role, DoctorRequestDto dto) {
        if (!"ADMIN".equals(role)) {
            throw new RuntimeException("Unauthorized: Only ADMIN can create doctors");
        }
        if (doctorRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        Doctor doctor = modelMapper.map(dto, Doctor.class);
        Doctor saved = doctorRepository.save(doctor);
        return mapToResponse(saved);
    }

    @Override
    public DoctorResponseDto updateDoctor(String id, String role, DoctorRequestDto dto) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        if (!"ADMIN".equals(role) && !"DOCTOR".equals(role)) {
            throw new RuntimeException("Unauthorized");
        }
        if ("DOCTOR".equals(role) && !doctor.getEmail().equals(dto.getEmail())) {
            throw new RuntimeException("Doctors can only update their own profile");
        }
        modelMapper.map(dto, doctor);
        Doctor saved = doctorRepository.save(doctor);
        return mapToResponse(saved);
    }

    @Override
    public void deleteDoctor(String id, String role) {
        if (!"ADMIN".equals(role)) {
            throw new RuntimeException("Unauthorized: Only ADMIN can delete doctors");
        }
        doctorRepository.deleteById(id);
    }

    @Override
    public List<AvailabilityDto> getSchedule(String id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        return mapAvailabilityEntitiesToDtos(doctor.getAvailability());
    }

    @Override
    public void updateSchedule(String id, String role, List<AvailabilityDto> availability) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        if (!"ADMIN".equals(role) && !"DOCTOR".equals(role)) {
            throw new RuntimeException("Unauthorized");
        }
        doctor.setAvailability(mapAvailabilityDtosToEntities(availability));
        doctorRepository.save(doctor);
    }

    private DoctorResponseDto mapToResponse(Doctor doctor) {
        DoctorResponseDto dto = modelMapper.map(doctor, DoctorResponseDto.class);
        dto.setAvailability(mapAvailabilityEntitiesToDtos(doctor.getAvailability()));
        return dto;
    }

    private List<Availability> mapAvailabilityDtosToEntities(List<AvailabilityDto> dtos) {
        return dtos != null ? dtos.stream().map(dto -> modelMapper.map(dto, Availability.class)).collect(Collectors.toList()) : null;
    }

    private List<AvailabilityDto> mapAvailabilityEntitiesToDtos(List<Availability> entities) {
        return entities != null ? entities.stream().map(entity -> modelMapper.map(entity, AvailabilityDto.class)).collect(Collectors.toList()) : null;
    }
}