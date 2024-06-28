package com.shyam.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private int userId;
    private int medicineId;

    @Min(value = 1, message = "minimum quantity must be grater than one")
    private int quantity;
    
    @NotBlank(message = "Phone number is required")
    private String phone;

    @NotBlank(message = "Delivery address is required")
    private String address;
}
