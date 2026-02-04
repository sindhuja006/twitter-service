package com.sindhuja.twitterservice.controller;

import com.sindhuja.twitterservice.dto.CreateTweetRequest;
import com.sindhuja.twitterservice.dto.TweetResponse;
import com.sindhuja.twitterservice.service.TweetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tweets")
public class TweetController {
     TweetService tweetService;
     public TweetController(TweetService tweetService){
         this.tweetService=tweetService;
     }
     @PostMapping("/{id}")
     public ResponseEntity<TweetResponse> postTweet(@RequestBody CreateTweetRequest request, @PathVariable String id){
         TweetResponse response=tweetService.postTweet(id,request);
         return ResponseEntity.status(HttpStatus.CREATED).body(response);
     }

     @GetMapping("/{id}")
     public ResponseEntity<List<TweetResponse>> getTweetById(@PathVariable String id){
         List<TweetResponse> responses=tweetService.getTweets(id);
         return ResponseEntity.ok(responses);
     }

     @DeleteMapping("users/{userId}/tweet/{tweetId}")
     public ResponseEntity<String> deleteTweet(@PathVariable String userId,@PathVariable int tweetId){
         tweetService.deleteTweetById(userId,tweetId);
         return ResponseEntity.noContent().build();
     }
}
