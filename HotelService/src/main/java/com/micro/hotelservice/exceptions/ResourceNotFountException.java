package com.micro.hotelservice.exceptions;

public class ResourceNotFountException extends RuntimeException {
    public ResourceNotFountException(String s) {
        super(s);
    }
    public ResourceNotFountException()
    {
        super("Resource not found !!");
    }
}
