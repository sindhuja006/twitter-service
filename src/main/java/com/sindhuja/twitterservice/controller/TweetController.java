package com.sindhuja.twitterservice.controller;

import com.sindhuja.twitterservice.domain.TweetIdNotExistsException;
import com.sindhuja.twitterservice.domain.UserNotExistsException;
import com.sindhuja.twitterservice.dto.CreateTweetRequest;
import com.sindhuja.twitterservice.dto.TweetResponse;
import com.sindhuja.twitterservice.dto.exception.ErrorResponse;
import com.sindhuja.twitterservice.service.TweetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("/tweets")
public class TweetController {
     private static final Logger log = LoggerFactory.getLogger(TweetController.class);
     TweetService tweetService;
     public TweetController(TweetService tweetService){
         this.tweetService=tweetService;
     }
     @PostMapping("/{id}")
     public ResponseEntity<Object> postTweet(@RequestBody CreateTweetRequest request, @PathVariable String id){
         TweetResponse response;
         try {
             response=tweetService.postTweet(id, request);
         }catch (UserNotExistsException ex){
             log.error("e",ex);
             return new ResponseEntity<>(new ErrorResponse(ex.getMessage()),ex.getStatus());
         }
         return ResponseEntity.status(HttpStatus.CREATED).body(response);
     }

     @GetMapping("/{id}")
     public ResponseEntity<List<?>> getTweets(@PathVariable String id){
         List<TweetResponse> responses;
         try{
             responses=tweetService.getTweets(id);

         } catch (UserNotExistsException ex) {
             log.error("e"+ex);
             List<Object> errorList=List.of(new ErrorResponse(ex.getMessage()));
             return new ResponseEntity<>(errorList,ex.getStatus());
         }
         return ResponseEntity.ok(responses);
     }

     @DeleteMapping("users/{userId}/tweet/{tweetId}")
     public ResponseEntity<Object> deleteTweet(@PathVariable String userId,@PathVariable int tweetId){
         try {
             tweetService.deleteTweet(userId, tweetId);
         }catch (UserNotExistsException e){
             return new ResponseEntity<>(new ErrorResponse(e.getMessage()),e.getStatus());
         }catch(TweetIdNotExistsException ex){
             return new ResponseEntity<>(new ErrorResponse(ex.getMessage()),ex.getStatus());
         }
         return ResponseEntity.noContent().build();
     }
}
