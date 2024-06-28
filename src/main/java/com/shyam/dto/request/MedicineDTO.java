package com.shyam.dto.request;

import org.springframework.web.multipart.MultipartFile;

import com.shyam.validators.FileRequired;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicineDTO {
    @Min(value = 1, message = "price must be grater than zero")
    private int price;

    @NotBlank(message = "Name is requried")
    private String name;
    
    @NotBlank(message = "Description is requried")
    private String description;

    @FileRequired
    private MultipartFile file;
}
