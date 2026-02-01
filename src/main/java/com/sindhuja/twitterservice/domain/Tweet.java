package com.sindhuja.twitterservice.domain;

import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;

import java.time.LocalDateTime;

public class Tweet {
    UserId userId;
    TweetId tweetId;
    String message;
    LocalDateTime insertTime;

    public Tweet(UserId userId, TweetId tweetId, String message, LocalDateTime insertTime) {
        this.userId = userId;
        this.tweetId = tweetId;
        this.message = message;
        this.insertTime = insertTime;
    }

    public UserId getUserId() {
        return userId;
    }

    public void setUserId(UserId userId) {
        this.userId = userId;
    }

    public TweetId getTweetId() {
        return tweetId;
    }

    public void setTweetId(TweetId tweetId) {
        this.tweetId = tweetId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(LocalDateTime insertTime) {
        this.insertTime = insertTime;
    }
}
