package com.shyam.exceptions;

public class UserExistsException extends Exception{

    public UserExistsException() {
        super("User already exists with same mail id");
    }

    public UserExistsException(String msg) {
        super(msg);
    }

}
