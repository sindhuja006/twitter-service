package com.sindhuja.twitterservice.repository;

import com.sindhuja.twitterservice.domain.Tweet;
import com.sindhuja.twitterservice.domain.TweetId;
import com.sindhuja.twitterservice.domain.UserId;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class TweetRepositoryImpl implements ITweetRepository{
    Map<UserId,Tweet> tweetMap=new HashMap<>();
    @Override
    public Tweet postTweet(UserId userId,Tweet tweet) {
        Tweet tweet1=tweetMap.put(userId,tweet);
        return tweet1;
    }

    @Override
    public void deleteTweet(UserId userId) {
        tweetMap.remove(userId);
    }

    @Override
    public Tweet getTweets(UserId userId) {
        Tweet tweet=tweetMap.get(userId);
        return tweet;
    }
}
