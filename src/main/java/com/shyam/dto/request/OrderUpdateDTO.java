package com.shyam.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateDTO {

    @Min(value = 1, message = "Order id is required")
    private int id;

    @NotBlank(message = "status of order is required")
    private String status;
}