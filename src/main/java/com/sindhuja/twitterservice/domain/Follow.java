package com.sindhuja.twitterservice.domain;

public class Follow {
    UserId followId;
    UserId followeeId;
    public UserId getFollowId() {
        return followId;
    }

    public void setFollowId(UserId followId) {
        this.followId = followId;
    }

    public UserId getFolloweeId() {
        return followeeId;
    }

    public void setFolloweeId(UserId followeeId) {
        this.followeeId = followeeId;
    }
}
