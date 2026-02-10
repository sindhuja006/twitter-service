package com.sindhuja.twitterservice.repository;

import com.sindhuja.twitterservice.domain.UserId;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class FollowRepositoryImpl implements IFollowRepository{

    Map<UserId,Set<UserId>> followersMap=new HashMap<>();
    Map<UserId,Set<UserId>> followingMap=new HashMap<>();
    @Override
    public void followUser(UserId followId, UserId followeeId) {
        followingMap.computeIfAbsent(followId,v->new HashSet<>()).add(followeeId);
        followersMap.computeIfAbsent(followeeId,v->new HashSet<>()).add(followId);
    }

    @Override
    public void unFollowUser(UserId followId, UserId followeeId) {
        Set<UserId> followeeSet=followingMap.get(followId);
        followeeSet.remove(followeeId);
        Set<UserId> followerSet=followersMap.get(followeeId);
        followerSet.remove(followId);
    }

    @Override
    public Set<UserId> getFollowers(UserId id) {
        Set<UserId> followers=followersMap.get(id);
        return followers;
    }

    public Set<UserId> getFollowing(UserId id){
        Set<UserId> following=followingMap.get(id);
        return following;
    }
}
