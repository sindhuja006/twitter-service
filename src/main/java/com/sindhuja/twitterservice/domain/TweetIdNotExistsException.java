package com.sindhuja.twitterservice.domain;

import org.springframework.http.HttpStatus;

public class TweetIdNotExistsException extends RuntimeException{
    private final HttpStatus status;
    public TweetIdNotExistsException(String message, HttpStatus status){
        super(message);
        this.status=status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
