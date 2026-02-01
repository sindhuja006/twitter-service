package com.sindhuja.twitterservice.service;
import com.sindhuja.twitterservice.domain.Tweet;
import com.sindhuja.twitterservice.domain.TweetId;
import com.sindhuja.twitterservice.domain.UserId;
import com.sindhuja.twitterservice.dto.CreateTweetRequest;
import com.sindhuja.twitterservice.dto.TweetResponse;
import com.sindhuja.twitterservice.repository.TweetRepositoryImpl;
import org.springframework.stereotype.Service;

@Service
public class TweetService {
     TweetRepositoryImpl tweetRepository;
     public TweetService(TweetRepositoryImpl tweetRepository){
         this.tweetRepository=tweetRepository;
     }

     public TweetResponse postTweet(String userId, CreateTweetRequest request){
         UserId userId1=new UserId(userId);
         TweetId tweetId=new TweetId(request.tweetId());
         Tweet tweet=new Tweet(userId1,tweetId,request.message(),request.insertTime());
         tweetRepository.postTweet(userId1,tweet);
         return new TweetResponse(userId, request.tweetId(), request.message(), request.insertTime());
     }

     public TweetResponse getTweets(String userId){
         UserId userId1=new UserId(userId);
         Tweet tweet=tweetRepository.getTweets(userId1);
         int tweetId=tweet.getTweetId().getTweetValue();
         return new TweetResponse(userId,tweetId,tweet.getMessage(),tweet.getInsertTime());
     }

     public void deleteTweetById(String userId){
         UserId userId1=new UserId(userId);
         tweetRepository.deleteTweet(userId1);
     }
}
