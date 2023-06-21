package com.example.minipropertymanagement.exception;

public class InvalidCredential extends RuntimeException{
    public InvalidCredential(String message) {
        super(message);
    }
}
