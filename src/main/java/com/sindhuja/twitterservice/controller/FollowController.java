package com.sindhuja.twitterservice.controller;

import com.sindhuja.twitterservice.dto.FollowResponse;
import com.sindhuja.twitterservice.service.FollowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FollowController {
   FollowService followService;
   public FollowController(FollowService followService){
       this.followService=followService;
   }

   @PostMapping
   public ResponseEntity<String> followUser(@PathVariable String followId,@PathVariable String followeeId){
       followService.followUser(followId,followeeId);
       return ResponseEntity.noContent().build();
   }

   @PostMapping
   public ResponseEntity<String> unFollowUser(@PathVariable String followId, @PathVariable String followeeId){
       followService.unFollowUser(followId, followeeId);
       return ResponseEntity.noContent().build();
   }

   @GetMapping("/{id}")
   public ResponseEntity<FollowResponse> getFollowersId(@PathVariable String id){
       FollowResponse response=followService.getFollowers(id);
       return ResponseEntity.ok(response);
   }

    @GetMapping("/{id}")
    public ResponseEntity<FollowResponse> getFollowingId(@PathVariable String id){
        FollowResponse response=followService.getFollowing(id);
        return ResponseEntity.ok(response);
    }
}
