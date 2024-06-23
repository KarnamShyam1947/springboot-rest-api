package com.shyam.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shyam.dto.request.MedicineDTO;
import com.shyam.entities.MedicineEntity;
import com.shyam.exceptions.EntityNotFoundException;
import com.shyam.services.MedicineService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/medicine")
@Tag(
    name = "Medicine Controller",
    description = "This controller manages the Medicines CRUD operations. Only For admin"
)
public class MedicineController {
    
    private final MedicineService medicineService;

    @GetMapping("/")
    public List<MedicineEntity> getAll() {
        return medicineService.getAll();
    }
   
    @PostMapping("/")
    public ResponseEntity<?> add(@RequestBody MedicineDTO dto) {
        MedicineEntity save = medicineService.save(dto);

        if (save == null) {
            throw new EntityExistsException("Medicine alreay exists with name : " + dto.getName());
        }
        System.out.println("save...........");
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(save);
    }
    
    @GetMapping("/{medicineId}")
    public ResponseEntity<?> get(
        @PathVariable("medicineId") int medicineId
    ) throws EntityNotFoundException {
        MedicineEntity medicine = medicineService
                                    .getById(medicineId)
                                    .orElseThrow(() -> new EntityNotFoundException("Requested Medicine Not found with id : " + medicineId));
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(medicine);
    }

    @PutMapping("/{medicineId}")
    public ResponseEntity<?> update(
        @PathVariable("medicineId") int medicineId,
        @RequestBody MedicineDTO dto
    ) throws EntityNotFoundException {
        medicineService
            .getById(medicineId)
            .orElseThrow(() -> new EntityNotFoundException("Requested Medicine Not found with id : " + medicineId));

        MedicineEntity medicine = medicineService.update(medicineId, dto);
        System.out.println(medicine)
        ;
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(medicine);
    }

    @DeleteMapping("/{medicineId}")
    public ResponseEntity<?> delete(
        @PathVariable("medicineId") int medicineId
    ) throws EntityNotFoundException {
        MedicineEntity medicine = medicineService
                                    .getById(medicineId)
                                    .orElseThrow(() -> new EntityNotFoundException("Requested Medicine Not found with id : " + medicineId));

        medicineService.delete(medicine);
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(Map.of("message", "Medicine deleted successfully"));
    }

}
