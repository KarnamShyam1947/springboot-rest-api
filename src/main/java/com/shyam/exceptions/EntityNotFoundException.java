package com.shyam.exceptions;

public class EntityNotFoundException extends Exception {
    public EntityNotFoundException() {
        super("Requested Entity Not Found");
    }
    
    public EntityNotFoundException(String str) {
        super(str);
    }
}
