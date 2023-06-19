package com.example.minipropertymanagement.exception;

public class ForbiddenAccess extends RuntimeException{

    public ForbiddenAccess(String message) {
        super(message);
    }

}
