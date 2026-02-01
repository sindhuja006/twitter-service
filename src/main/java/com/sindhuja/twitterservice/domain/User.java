package com.sindhuja.twitterservice.domain;

public class User {
    private UserId userId;
    private String name;
    private String email;

    public User(UserId userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public UserId getUserId() {
        return userId;
    }

    public void setUserId(UserId userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
