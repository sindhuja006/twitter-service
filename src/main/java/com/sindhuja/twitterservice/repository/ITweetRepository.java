package com.sindhuja.twitterservice.repository;

import com.sindhuja.twitterservice.domain.Tweet;
import com.sindhuja.twitterservice.domain.TweetId;
import com.sindhuja.twitterservice.domain.UserId;

public interface ITweetRepository {
    Tweet postTweet(UserId userId, Tweet tweet);
    void deleteTweet(UserId userId);
    Tweet getTweets(UserId userId);
}
