package com.shyam.exceptions;

public class EntityAlreadyExistsException extends Exception {
    EntityAlreadyExistsException() {
        super("Entity Alreay Exists");
    }
    
    EntityAlreadyExistsException(String str) {
        super(str);
    }
}
