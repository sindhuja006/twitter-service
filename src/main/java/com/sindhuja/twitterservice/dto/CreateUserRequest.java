package com.sindhuja.twitterservice.dto;

import com.sindhuja.twitterservice.domain.UserId;

public record CreateUserRequest (
        String userId,
        String name,
        String email
){}
