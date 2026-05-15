package com.sindhuja.twitterservice.domain;


import java.time.LocalDateTime;

public class Tweet {
    private final UserId userId;
    private final TweetId tweetId;
    private final String message;
    private final LocalDateTime insertTime;

    public Tweet(Builder builder) {
        this.userId = builder.userId;
        this.tweetId = builder.tweetId;
        this.message = builder.message;
        this.insertTime = builder.insertTime;
    }

    public static Builder builder(){
        return new Builder();
    }

    public UserId getUserId() {
        return userId;
    }
    public TweetId getTweetId() {
        return tweetId;
    }
    public String getMessage() {
        return message;
    }
    public LocalDateTime getInsertTime() {
        return insertTime;
    }

    public static class Builder{
        private UserId userId;
        private TweetId tweetId;
        private String message;
        private LocalDateTime insertTime;

        public Builder userId(UserId userId){
            this.userId=userId;
            return this;
        }

        public Builder tweetId(TweetId tweetId){
            this.tweetId=tweetId;
            return this;
        }

        public Builder message(String message){
            this.message=message;
            return this;
        }

        public Builder insertTime(LocalDateTime insertTime){
            this.insertTime=insertTime;
            return this;
        }

        public Tweet build(){
            return new Tweet(this);
        }
    }

}
