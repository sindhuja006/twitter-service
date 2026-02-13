package com.sindhuja.twitterservice.repository;

import com.sindhuja.twitterservice.domain.Tweet;
import com.sindhuja.twitterservice.domain.TweetId;

import com.sindhuja.twitterservice.domain.TweetIdNotExistsException;
import com.sindhuja.twitterservice.domain.UserId;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TweetRepositoryImpl implements ITweetRepository{
    Map<UserId, List<Tweet>> tweetMap=new HashMap<>();

    @Override
    public List<Tweet> postTweet(UserId userId, Tweet tweet) {
        tweetMap.computeIfAbsent(userId,v->new LinkedList<>()).add(tweet);
        List<Tweet> tweetList=tweetMap.get(userId);
        return tweetList;
    }

    //To be Refractor
    @Override
    public void deleteTweet(UserId userId,TweetId tweetId) {
        List<Tweet> tweetList=tweetMap.get(userId);
        tweetList.removeIf(tweet -> tweet.getTweetId().equals(tweetId));
    }

    @Override
    public List<Tweet> getTweets(UserId userId) {
        List<Tweet> tweetList=tweetMap.get(userId);
        return tweetList;
    }



    @Override
    public void verifyTweetExists(UserId userId, TweetId tweetId) {
        List<Tweet> tweetList=tweetMap.get(userId);
        int count=0;
        for(Tweet tweet:tweetList){
            if(tweet.getTweetId().equals(tweetId)){
                count++;
            }
        }
        if(count==0){
            throw new TweetIdNotExistsException("TweetId" + tweetId +"not exists", HttpStatus.CONFLICT);
        }
    }


}
