package com.shyam.exceptions;

public class AuthorizationHeaderMissingException extends Exception {
    String path;

    public AuthorizationHeaderMissingException(String path) {
        super("JWT Authorization Header is missing");
        this.path = path;
    }

    public AuthorizationHeaderMissingException(String msg, String path) {
        super(msg);
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
    
}
