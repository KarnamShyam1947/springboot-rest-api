package com.shyam.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequest {
    @NotBlank(message = "date is required")
    private String date;

    @NotBlank(message = "slot is required")
    private String slot;
    
    @NotBlank(message = "reason is required")
    private String reason;

    @Min(value = 1, message = "user id is required")
    private int userId;  

    @Min(value = 1, message = "doctor id is required")
    private int doctorId;    
}
