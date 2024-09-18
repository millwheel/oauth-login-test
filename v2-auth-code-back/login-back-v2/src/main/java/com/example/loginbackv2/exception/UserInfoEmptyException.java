package com.example.loginbackv2.exception;

public class UserInfoEmptyException extends RuntimeException{

    public UserInfoEmptyException() {
        super("user info empty");
    }
}
