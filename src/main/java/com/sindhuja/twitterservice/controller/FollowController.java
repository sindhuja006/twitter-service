package com.sindhuja.twitterservice.controller;
import com.sindhuja.twitterservice.dto.FollowResponse;
import com.sindhuja.twitterservice.service.FollowService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * flow
 */

@RequestMapping("/api/follows")
@RestController
public class FollowController {
   FollowService followService;
   public FollowController(FollowService followService){
       this.followService=followService;
   }

   @PostMapping("/follow/{followId}/followee/{followeeId}")
   public ResponseEntity<String> followUser(@PathVariable String followId,@PathVariable String followeeId){
       followService.followUser(followId,followeeId);
       return ResponseEntity.status(HttpStatus.CREATED).build();
   }

   @PutMapping("/follow/{followId}/followee/{followeeId}")
   public ResponseEntity<String> unFollowUser(@PathVariable String followId, @PathVariable String followeeId){
       followService.unFollowUser(followId, followeeId);
       return ResponseEntity.noContent().build();
   }

   @GetMapping("/{id}/followers")
   public ResponseEntity<FollowResponse> getFollowersId(@PathVariable String id){
       FollowResponse response=followService.getFollowers(id);
       return ResponseEntity.ok(response);
   }

    @GetMapping("/{id}/following")
    public ResponseEntity<FollowResponse> getFollowingId(@PathVariable String id){
        FollowResponse response=followService.getFollowing(id);
        return ResponseEntity.ok(response);
    }
}
