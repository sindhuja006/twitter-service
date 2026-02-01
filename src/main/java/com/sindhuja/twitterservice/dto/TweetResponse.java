package com.sindhuja.twitterservice.dto;

import com.sindhuja.twitterservice.domain.TweetId;
import com.sindhuja.twitterservice.domain.UserId;

import java.time.LocalDateTime;

public record TweetResponse (
        String userId,
        int tweetId,
        String message,
        LocalDateTime insertTime
){}
