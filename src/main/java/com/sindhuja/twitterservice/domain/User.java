package com.sindhuja.twitterservice.domain;

import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;

import java.util.Objects;

public class User {
    private final UserId userId;
    private final String name;
    private final String email;

    public User(Builder builder) {
        this.userId = builder.userId;
        this.name = builder.name;
        this.email = builder.email;
    }

    public static Builder builder(){
        return new Builder();
    }
    public UserId getUserId() {
        return userId;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }

    public static class Builder{
        private UserId userId;
        private String name;
        private String email;

        public  Builder userId(UserId userId){
            this.userId=userId;
            return this;
        }

        public Builder name(String name){
            this.name=name;
            return this;

        }

        public Builder email(String email){
            this.email=email;
            return this;
        }

        public User build(){
            return new User(this);
        }

    }



    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User user)) return false;
        return Objects.equals(userId, user.userId) && Objects.equals(name, user.name) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, name, email);
    }
}
