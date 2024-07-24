package com.udemy.securityjwt.exception;

public class RoleNotAddedException extends RuntimeException{
    public RoleNotAddedException(String message) {
        super(message);
    }
}
