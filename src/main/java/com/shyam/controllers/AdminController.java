package com.shyam.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shyam.dto.request.DepartmentRequest;
import com.shyam.dto.request.DoctorRequest;
import com.shyam.dto.request.OrderUpdateDTO;
import com.shyam.entities.DepartmentEntity;
import com.shyam.entities.UserEntity;
import com.shyam.exceptions.EntityAlreadyExistsException;
import com.shyam.exceptions.EntityNotFoundException;
import com.shyam.services.DepartmentService;
import com.shyam.services.DoctorService;
import com.shyam.services.OrderService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
@Tag(
    name = "Admin Controller",
    description = "All endpoint only admin can access"
)
public class AdminController {

    private final OrderService orderService;
    private final DoctorService doctorService;
    private final DepartmentService departmentService;
    
    @PostMapping("/doctor")
    public ResponseEntity<?> addDoctor(
        @Valid @RequestBody DoctorRequest doctorRequest
    ) throws EntityAlreadyExistsException {

        UserEntity doctor = doctorService.addDoctor(doctorRequest);
        if (doctor == null) 
            throw new EntityAlreadyExistsException("User already exists with same mail");
        
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(doctor);
    }
    
    @PostMapping("/department")
    public ResponseEntity<?> addDepartment(
        @Valid @RequestBody DepartmentRequest request
    ) throws EntityAlreadyExistsException {

        DepartmentEntity department = departmentService.addDepartment(request);  
        
        if (department == null) 
            throw new EntityAlreadyExistsException("Already entity exists with same name : " + request.getName());
        
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(department);
    }

    @DeleteMapping("/doctor/{doctorId}")
    public ResponseEntity<?> deleteDoctor(
        @PathVariable("doctorId") int doctorId
    ) {
        doctorService.deleteDoctor(doctorId);
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(Map.of("message", "doctor removed successfully"));
    }
    
    @DeleteMapping("/department/{departmentId}")
    public ResponseEntity<?> deleteDepartment(
        @PathVariable("departmentId") int departmentId
    ) throws EntityAlreadyExistsException {
        departmentService.deleteDepartment(departmentId);
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(Map.of("message", "department removed successfully"));
    }

    @PutMapping("/change-order-status")
    public ResponseEntity<?> orderStatus(
        @RequestBody OrderUpdateDTO orderUpdateDTO
    ) throws EntityNotFoundException {
        orderService.changeStatus(orderUpdateDTO);  
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(Map.of("message", "order status changed successfully")); 
    }

}
