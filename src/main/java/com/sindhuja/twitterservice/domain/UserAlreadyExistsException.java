package com.sindhuja.twitterservice.domain;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends RuntimeException {

    private final HttpStatus status;

    public UserAlreadyExistsException(String message, HttpStatus status) {
        super(message);
        this.status=status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
