package com.shyam.exceptions;

public class CustomAccessDeniedException extends Exception {
    public String path = "/";

    public CustomAccessDeniedException(String path) {
        super("Authenticated user not have access to access this resource");
        this.path = path;
    }

    public CustomAccessDeniedException(String msg, String path) {
        super(msg);
        this.path = path;
    }
    
    public String getPath() {
        return this.path;
    }
}
