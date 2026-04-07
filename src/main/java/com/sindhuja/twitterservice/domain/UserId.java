package com.sindhuja.twitterservice.domain;

import java.util.Objects;

public class UserId {
    String value;
    public String getUserValue(){
        return value;
    }

    public UserId(String userId) {
        this.value = userId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UserId userId1)) return false;
        return Objects.equals(value, userId1.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return value;
    }
}

