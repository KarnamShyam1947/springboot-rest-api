package com.shyam.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.shyam.dto.request.DoctorRequest;
import com.shyam.entities.UserEntity;
import com.shyam.enums.Role;
import com.shyam.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorService {

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

    public void deleteDoctor(int id) {
        UserEntity doctor = userRepository.findById(id);
        userRepository.delete(doctor);
    }
    
}
