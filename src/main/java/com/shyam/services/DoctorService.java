package com.shyam.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shyam.dto.request.DoctorRequest;
import com.shyam.entities.UserEntity;
import com.shyam.enums.Role;
import com.shyam.repositories.AppointmentRepository;
import com.shyam.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final ModelMapper mapper;

    public UserEntity addDoctor(DoctorRequest request) {
        UserEntity user = mapper.map(request, UserEntity.class);

        if (userRepository.findByEmail(user.getEmail()) != null) 
            return null;    
        
        // send mail
        user.setId(0);
        user.setRole(Role.DOCTOR);
        UserEntity newUser = userRepository.save(user);

        emailService.sendSetPasswordEmail(newUser);
        
        return newUser;
    }  
    
    public List<UserEntity> getAllDoctors() {
        return userRepository.findByRole(Role.DOCTOR);
    }

    public List<UserEntity> getDoctorsByDepartment(String department) {
        return userRepository.findByDepartment(department);
    }

    @Transactional
    public void deleteDoctor(int id) {
        appointmentRepository.deleteByDoctorId(id);
        UserEntity doctor = userRepository.findById(id);
        userRepository.delete(doctor);
    }
    
}
