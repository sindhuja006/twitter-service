package com.sindhuja.twitterservice.controller;
import com.sindhuja.twitterservice.domain.UserNotExistsException;
import com.sindhuja.twitterservice.dto.FollowResponse;
import com.sindhuja.twitterservice.dto.exception.ErrorResponse;
import com.sindhuja.twitterservice.service.FollowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * flow
 */

@RequestMapping("/api/follows")
@RestController
public class FollowController {
    private static final Logger log = LoggerFactory.getLogger(FollowController.class);
   FollowService followService;
   public FollowController(FollowService followService){
       this.followService=followService;
   }

   @PostMapping("/follow/{followId}/followee/{followeeId}")
   public ResponseEntity<Object> followUser(@PathVariable String followId,@PathVariable String followeeId){
       try {
           followService.followUser(followId, followeeId);
       }catch (UserNotExistsException ex){
           log.error("e:",ex);
           return new ResponseEntity<>(new ErrorResponse(ex.getMessage()),ex.getStatus());
       }
       return ResponseEntity.status(HttpStatus.CREATED).build();
   }

   @PutMapping("/follow/{followId}/followee/{followeeId}")
   public ResponseEntity<Object> unFollowUser(@PathVariable String followId, @PathVariable String followeeId){
       try {
           followService.unFollowUser(followId, followeeId);
       } catch (UserNotExistsException ex) {
           log.error("e:",ex);
           return new ResponseEntity<>(new ErrorResponse(ex.getMessage()),ex.getStatus());
       }
       return ResponseEntity.noContent().build();
   }

   @GetMapping("/{id}/followers")
   public ResponseEntity<Object> getFollowersId(@PathVariable String id){
       FollowResponse response;
       try {
            response = followService.getFollowers(id);
       }catch(UserNotExistsException ex){
           log.error("e:",ex);
           return new ResponseEntity<>(new ErrorResponse(ex.getMessage()),ex.getStatus());
       }
       return ResponseEntity.ok(response);
   }

    @GetMapping("/{id}/following")
    public ResponseEntity<Object> getFollowingId(@PathVariable String id){
        FollowResponse response;
       try {
           response = followService.getFollowing(id);
       }catch (UserNotExistsException ex){
           log.error("e:",ex);
           return new ResponseEntity<>(new ErrorResponse(ex.getMessage()),ex.getStatus());
       }
        return ResponseEntity.ok(response);
    }
}
