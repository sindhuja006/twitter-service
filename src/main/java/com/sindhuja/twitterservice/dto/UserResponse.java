package com.sindhuja.twitterservice.dto;

import com.sindhuja.twitterservice.domain.UserId;

public record UserResponse(
        String userId,
        String name,
        String email
){}
