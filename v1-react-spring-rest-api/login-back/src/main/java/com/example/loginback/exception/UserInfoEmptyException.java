package com.example.loginback.exception;

public class UserInfoEmptyException extends RuntimeException{

    public UserInfoEmptyException() {
        super("user info empty");
    }
}
