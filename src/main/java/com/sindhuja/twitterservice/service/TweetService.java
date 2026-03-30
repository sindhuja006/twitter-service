package com.sindhuja.twitterservice.service;
import com.sindhuja.twitterservice.domain.Tweet;
import com.sindhuja.twitterservice.domain.TweetId;
import com.sindhuja.twitterservice.domain.UserId;
import com.sindhuja.twitterservice.dto.CreateTweetRequest;
import com.sindhuja.twitterservice.dto.TweetResponse;
import com.sindhuja.twitterservice.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TweetService {
     ITweetRepository tweetRepository;
     IUserRepository userRepository;
     IFollowRepository followRepository;
     public TweetService(ITweetRepository tweetRepository, IUserRepository userRepository,IFollowRepository followRepository){
         this.tweetRepository=tweetRepository;
         this.userRepository=userRepository;
         this.followRepository=followRepository;
     }

     public TweetResponse postTweet(String userId, CreateTweetRequest request){
             UserId userId1 = new UserId(userId);
             userRepository.verifyUserExists(userId1);
             TweetId tweetId = new TweetId(request.tweetId());
             Tweet tweet = new Tweet(userId1, tweetId, request.message(), request.insertTime());
             Tweet resultTweet=tweetRepository.postTweet(userId1, tweet);
             return new TweetResponse(resultTweet.getTweetId().getTweetValue(), resultTweet.getMessage(), resultTweet.getInsertTime());
     }

     public List<TweetResponse> getTweets(String userId){
         UserId userId1=new UserId(userId);
         userRepository.verifyUserExists(userId1);
         List<Tweet> tweetList=tweetRepository.getTweets(userId1);
         List<TweetResponse> responses=new ArrayList<>();
         for(Tweet tweet:tweetList){
             int tweetId=tweet.getTweetId().getTweetValue();
             TweetResponse response=new TweetResponse(tweetId,tweet.getMessage(),tweet.getInsertTime());
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

     public Set<TweetResponse> getTimeLine(String userId){
         UserId userId1=new UserId(userId);
         Set<UserId> followingSet=followRepository.getFollowing(userId1);
         followingSet.add(userId1);
         Set<TweetResponse> responses=new HashSet<>();
         for(UserId id:followingSet){
             List<Tweet> tweetList=tweetRepository.getTweets(id);
             for(Tweet tweet:tweetList){
                 int tweetId=tweet.getTweetId().getTweetValue();
                 TweetResponse response=new TweetResponse(tweetId,tweet.getMessage(),tweet.getInsertTime());
                 responses.add(response);
             }
         }
         return responses;
     }
}
