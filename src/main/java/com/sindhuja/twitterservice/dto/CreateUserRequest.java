package com.sindhuja.twitterservice.dto;

public record CreateUserRequest (
        String userId,
        String name,
        String email
){}
