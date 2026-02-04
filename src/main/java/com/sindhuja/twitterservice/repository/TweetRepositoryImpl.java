package com.sindhuja.twitterservice.repository;

import com.sindhuja.twitterservice.domain.Tweet;
import com.sindhuja.twitterservice.domain.TweetId;
import com.sindhuja.twitterservice.domain.UserId;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Repository
public class TweetRepositoryImpl implements ITweetRepository{
    Map<UserId, List<Tweet>> tweetMap=new HashMap<>();
    @Override
    public List<Tweet> postTweet(UserId userId, Tweet tweet) {
        tweetMap.computeIfAbsent(userId,v->new LinkedList<>()).add(tweet);
        List<Tweet> tweetList=tweetMap.get(userId);
        return tweetList;
    }

    @Override
    public void deleteTweet(UserId userId,TweetId tweetId) {
        List<Tweet> tweetList=tweetMap.get(userId);
        for(Tweet tweet:tweetList){
            if(tweet.getTweetId().equals(tweetId)){
                tweetList.remove(tweet);
            }
        }
    }

    @Override
    public List<Tweet> getTweets(UserId userId) {
        List<Tweet> tweetList=tweetMap.get(userId);
        return tweetList;
    }
}
