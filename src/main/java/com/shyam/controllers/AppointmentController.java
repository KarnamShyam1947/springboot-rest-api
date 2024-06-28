package com.shyam.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shyam.dto.request.AppointmentRequest;
import com.shyam.entities.AppointmentEntity;
import com.shyam.exceptions.EntityAlreadyExistsException;
import com.shyam.services.AppointmentService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/appointment")
@Tag(
    name = "Appointment Controller",
    description = "This controller manages the appointment of patients"
)
public class AppointmentController {
    
    private final AppointmentService appointmentService;

    @PostMapping("/make-appointment")
    public ResponseEntity<?> addAppointment(
        @Valid @RequestBody AppointmentRequest appointmentRequest
    ) throws EntityAlreadyExistsException {
        System.out.println(appointmentRequest);
        AppointmentEntity appointment = appointmentService.addAppointment(appointmentRequest);

        if (appointment == null) 
            throw new EntityAlreadyExistsException("Appointment already booked for same slot and time");

        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(Map.of("message", "appointment booked successfully"));
        
    }

    @GetMapping("/my-appointments")
    public ResponseEntity<?> getAppointments() {
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(appointmentService.getUserAppointments());
    }

}
