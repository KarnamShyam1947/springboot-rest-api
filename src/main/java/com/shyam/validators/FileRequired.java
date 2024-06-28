package com.shyam.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.shyam.validators.impl.FileRequriedValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;

@Documented
@Target(value = {ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { FileRequriedValidator.class })
public @interface FileRequired {
    String message() default "File required";

    long maxSize() default  1048576L;

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
