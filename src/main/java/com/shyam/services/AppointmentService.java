package com.shyam.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.shyam.dto.request.AppointmentRequest;
import com.shyam.entities.AppointmentEntity;
import com.shyam.entities.UserEntity;
import com.shyam.enums.Role;
import com.shyam.repositories.AppointmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AuthService authService;
    private final ModelMapper mapper;
    
    public AppointmentEntity addAppointment(AppointmentRequest request) {
        try {
            System.out.println(request);
            
            AppointmentEntity appointment = mapper.map(request, AppointmentEntity.class);
            appointment.setId(0);
            return appointmentRepository.save(appointment);
        } 
        catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }


    public List<?> getUserAppointments() {
        UserEntity currentUser = authService.getCurrentUser();

        if (currentUser.getRole().equals(Role.DOCTOR)) 
            return appointmentRepository.getDoctorAppointments(currentUser.getId());
        
        else
            return appointmentRepository.getPatientAppointments(currentUser.getId());
    }

}
