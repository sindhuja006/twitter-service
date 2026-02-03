package com.sindhuja.twitterservice.repository;

import com.sindhuja.twitterservice.domain.User;
import com.sindhuja.twitterservice.domain.UserAlreadyExistsException;
import com.sindhuja.twitterservice.domain.UserId;
import com.sindhuja.twitterservice.domain.UserNotExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepositoryImpl implements IUserRepository{
    Map<UserId,User> userMap=new HashMap<>();
    @Override
    public User addUser(User user) {
        return userMap.computeIfAbsent(user.getUserId(),v->user);
    }

    @Override
    public void deleteUser(UserId userId) {
        userMap.remove(userId);
    }

    @Override
    public User updateUser(User user,UserId userId) {
        return userMap.computeIfPresent(user.getUserId(),(k,v)->user);
    }

    @Override
    public User getUserById(UserId userId) {
        User user1=userMap.get(userId);
        return user1;
    }

    @Override
    public void verifyUserNotExists(UserId userId) {
        if(userMap.containsKey(userId)) {
            throw new UserAlreadyExistsException("user "+userId+" already exists", HttpStatus.CONFLICT);
        }
    }

    public void verifyUserExists(UserId userId){
        if(!userMap.containsKey(userId)){
           throw new UserNotExistsException("user" + userId +"not exists",HttpStatus.CONFLICT);
        }
    }
}
