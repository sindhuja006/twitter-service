package com.sindhuja.twitterservice.dto;

import com.sindhuja.twitterservice.domain.UserId;

import java.util.Set;

public record FollowResponse (
        Set<UserId> idSet
){}
