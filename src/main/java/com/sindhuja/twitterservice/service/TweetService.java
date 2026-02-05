package com.sindhuja.twitterservice.service;
import com.sindhuja.twitterservice.domain.Tweet;
import com.sindhuja.twitterservice.domain.TweetId;
import com.sindhuja.twitterservice.domain.UserId;
import com.sindhuja.twitterservice.dto.CreateTweetRequest;
import com.sindhuja.twitterservice.dto.TweetResponse;
import com.sindhuja.twitterservice.repository.TweetRepositoryImpl;
import com.sindhuja.twitterservice.repository.UserRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TweetService {
     TweetRepositoryImpl tweetRepository;
     UserRepositoryImpl userRepository;
     public TweetService(TweetRepositoryImpl tweetRepository, UserRepositoryImpl userRepository){
         this.tweetRepository=tweetRepository;
         this.userRepository=userRepository;
     }

     public TweetResponse postTweet(String userId, CreateTweetRequest request){
             UserId userId1 = new UserId(userId);
             userRepository.verifyUserExists(userId1);
             TweetId tweetId = new TweetId(request.tweetId());
             Tweet tweet = new Tweet(userId1, tweetId, request.message(), request.insertTime());
             tweetRepository.postTweet(userId1, tweet);
             return new TweetResponse(userId, request.tweetId(), request.message(), request.insertTime());
     }

     public List<TweetResponse> getTweets(String userId){
         UserId userId1=new UserId(userId);
         userRepository.verifyUserExists(userId1);
         List<Tweet> tweetList=tweetRepository.getTweets(userId1);
         List<TweetResponse> responses=new ArrayList<>();
         for(Tweet tweet:tweetList){
             int tweetId=tweet.getTweetId().getTweetValue();
             TweetResponse response=new TweetResponse(userId,tweetId,tweet.getMessage(),tweet.getInsertTime());
             responses.add(response);
         }
         return responses;
     }

     public void deleteTweet(String userId,int tweetId){
         UserId userId1=new UserId(userId);
         userRepository.verifyUserExists(userId1);
         TweetId tweetId1=new TweetId(tweetId);
         tweetRepository.verifyTweetExists(userId1,tweetId1);
         tweetRepository.deleteTweet(userId1,tweetId1);
     }
}
