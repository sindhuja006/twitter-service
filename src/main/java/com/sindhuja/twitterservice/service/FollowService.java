package com.sindhuja.twitterservice.service;

import com.sindhuja.twitterservice.domain.UserId;
import com.sindhuja.twitterservice.dto.FollowResponse;
import com.sindhuja.twitterservice.repository.FollowRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class FollowService {
    FollowRepositoryImpl followRepository;
    public FollowService(FollowRepositoryImpl followRepository){
        this.followRepository=followRepository;
    }

    public void followUser(String followId,String followeeId){
        UserId followUserId=new UserId(followId);
        UserId followeeUserId=new UserId(followeeId);
        followRepository.followUser(followUserId,followeeUserId);
    }

    public void unFollowUser(String followId,String followeeId){
        UserId followUserId=new UserId(followId);
        UserId followeeUserId=new UserId(followeeId);
        followRepository.unFollowUser(followUserId,followeeUserId);
    }

    public FollowResponse getFollowers(String id){
        UserId userId=new UserId(id);
        Set<UserId> idSet=followRepository.getFollowers(userId);
        FollowResponse response=new FollowResponse(idSet);
        return response;
    }

    public FollowResponse getFollowing(String id){
        UserId userId=new UserId(id);
        Set<UserId> idSet=followRepository.getFollowing(userId);
        FollowResponse response=new FollowResponse(idSet);
        return response;
    }
}
