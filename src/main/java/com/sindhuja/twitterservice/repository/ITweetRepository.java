package com.sindhuja.twitterservice.repository;

import com.sindhuja.twitterservice.domain.Tweet;
import com.sindhuja.twitterservice.domain.TweetId;
import com.sindhuja.twitterservice.domain.UserId;

import java.util.List;
import java.util.Set;

public interface ITweetRepository {
    Tweet postTweet(UserId userId, Tweet tweet);
    void deleteTweet(UserId userId,TweetId tweetId);
    List<Tweet> getTweets(UserId userId);
    void verifyTweetExists(UserId userId,TweetId tweetId);

}
