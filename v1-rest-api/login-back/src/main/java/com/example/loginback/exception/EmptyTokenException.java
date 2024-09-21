package com.example.loginback.exception;

public class EmptyTokenException extends RuntimeException{

    public EmptyTokenException() {
        super("Empty token");
    }
}
