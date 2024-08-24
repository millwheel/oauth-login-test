package com.example.loginback.exception;

import org.springframework.http.HttpStatusCode;

public class RequestFailException extends RuntimeException{

    public RequestFailException(HttpStatusCode httpStatusCode) {
        super("request sending failed. status: " + httpStatusCode);
    }
}
