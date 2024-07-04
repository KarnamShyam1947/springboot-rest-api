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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shyam.dto.request.MedicineDTO;
import com.shyam.entities.MedicineEntity;
import com.shyam.exceptions.EntityAlreadyExistsException;
import com.shyam.exceptions.EntityNotFoundException;
import com.shyam.services.MedicineService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/medicine")
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
   
    // @PostMapping("/")
    @PostMapping(
    path = "/", 
    consumes = {"multipart/form-data"},
    produces = { "application/json" })
    ResponseEntity<?> add(
        @RequestParam(name = "file") MultipartFile file,
        @RequestPart(name = "name") String name,
        @RequestPart(name = "price") String price,
        @RequestPart(name = "description") String description
    ) throws EntityAlreadyExistsException {
        MedicineDTO dto = MedicineDTO
                            .builder()
                            .name(name)
                            .file(file)
                            .description(description)
                            .price(Integer.parseInt(price))
                            .build();

        MedicineEntity save = medicineService.save(dto);

        if (save == null) {
            throw new EntityAlreadyExistsException("Medicine already exists with name : " + dto.getName());
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
