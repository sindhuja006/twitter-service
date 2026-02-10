package com.sindhuja.twitterservice.repository;

import com.sindhuja.twitterservice.domain.UserId;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;

import java.util.List;
import java.util.Set;

public interface IFollowRepository {
    void followUser(UserId followerId,UserId followeeId);
    void unFollowUser(UserId followerId,UserId followeeId);
    Set<UserId> getFollowers(UserId followeeId);

}

