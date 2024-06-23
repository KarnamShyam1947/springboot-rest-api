package com.shyam.controllers;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shyam.entities.MedicineEntity;
import com.shyam.enums.Departments;
import com.shyam.services.MedicineService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(
    name = "Demo Controller",
    description = "This controller for testing purpose"
)
public class DemoController {

    private final MedicineService medicineService;

    public String[] get() {
        return new String[]{ "value1", "value2",
        "value3", "value4" };
    }

    @GetMapping("/")
    @Operation(
      summary = "Find XYZ",
      parameters = {
            @Parameter(
            in = ParameterIn.QUERY,
            name = "id",
            schema = @Schema(type = "string", implementation = Departments.class),
            description ="description ")
      })
    public String home() {
        return "Home";
    }
    
    @GetMapping("/medicines")
    public List<MedicineEntity> medicines() {
        return medicineService.getAll();
    }

    @GetMapping("/about")
    public Object about() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }

    @GetMapping("/public")
    public String publicMethod() {
        return "public page";
    }
}
