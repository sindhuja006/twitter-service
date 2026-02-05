package com.sindhuja.twitterservice.domain;

import java.util.Objects;

public class TweetId {
    int value;

    public int getTweetValue(){
        return value;
    }

    public TweetId(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TweetId tweetId)) return false;
        return value == tweetId.value;
    }

    @Override
    public String toString() {
        return "TweetId{" +
                "value=" + value +
                '}';
    }
}
