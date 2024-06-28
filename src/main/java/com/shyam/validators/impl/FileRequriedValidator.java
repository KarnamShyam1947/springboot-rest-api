package com.shyam.validators.impl;

import org.springframework.web.multipart.MultipartFile;

import com.shyam.validators.FileRequired;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileRequriedValidator implements ConstraintValidator<FileRequired, MultipartFile> {

    long size = 0L;

    @Override
    public void initialize(FileRequired constraintAnnotation) {
        size = constraintAnnotation.maxSize();
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        if (value == null || value.getSize() <= 0) {
            context
            .buildConstraintViolationWithTemplate("Please select a file")
            .addConstraintViolation();

            return false;
        }
        else if (value.getSize() > size) {
            context
            .buildConstraintViolationWithTemplate("selected file is grater than " + size + " bytes")
            .addConstraintViolation();

            return false;
        }
        return true;
    }

    
}
