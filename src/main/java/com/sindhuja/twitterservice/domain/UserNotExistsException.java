package com.sindhuja.twitterservice.domain;

import org.springframework.http.HttpStatus;

public class UserNotExistsException extends RuntimeException {

    private final HttpStatus status;
    public UserNotExistsException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
