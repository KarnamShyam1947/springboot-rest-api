package com.shyam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorAppointmentResponse {
    private int appointmentId;
    private String appointmentDate;
    private String appointmentSlot;
    private String reason;
    private int patientId;
    private String patientName;
    private String patientEmail;
}
