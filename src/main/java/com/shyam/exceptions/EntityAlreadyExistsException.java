package com.shyam.exceptions;

public class EntityAlreadyExistsException extends Exception {
    public EntityAlreadyExistsException() {
        super("Entity Already Exists");
    }
    
    public EntityAlreadyExistsException(String str) {
        super(str);
    }
}
