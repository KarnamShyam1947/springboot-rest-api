package com.shyam.dto.request;

import com.shyam.enums.Departments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentRequest {
    private Departments name;
    private String description;
}
