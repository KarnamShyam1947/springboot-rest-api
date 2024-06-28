package com.shyam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientAppointResponse {
    private int appointmentId;
    private String appointmentDate;
    private String appointmentSlot;
    private int doctorId;
    private String doctorName;
    private String doctorEmail;
    private String doctorDepartment;
}
