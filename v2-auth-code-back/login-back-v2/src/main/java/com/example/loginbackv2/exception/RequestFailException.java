package com.example.loginbackv2.exception;

import org.springframework.http.HttpStatusCode;

public class RequestFailException extends RuntimeException{

    public RequestFailException(HttpStatusCode httpStatusCode) {
        super("request sending failed. status: " + httpStatusCode);
    }
}
