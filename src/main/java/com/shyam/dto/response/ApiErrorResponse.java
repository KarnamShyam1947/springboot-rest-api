package com.shyam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class ApiErrorResponse {
    private Date date;
    private String path;
    private String message;
    private int statusCode;
    private Map<String, Object> errors;

    public ApiErrorResponse() {
        this.date = new Date();
    }
}
