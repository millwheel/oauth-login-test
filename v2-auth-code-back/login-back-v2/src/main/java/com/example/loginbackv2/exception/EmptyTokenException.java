package com.example.loginbackv2.exception;

public class EmptyTokenException extends RuntimeException{

    public EmptyTokenException() {
        super("Empty token");
    }
}
