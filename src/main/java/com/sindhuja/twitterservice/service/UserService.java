package com.sindhuja.twitterservice.service;

import com.sindhuja.twitterservice.dto.CreateUserRequest;
import com.sindhuja.twitterservice.domain.User;
import com.sindhuja.twitterservice.domain.UserId;
import com.sindhuja.twitterservice.dto.UserResponse;
import com.sindhuja.twitterservice.repository.UserRepositoryImpl;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    UserRepositoryImpl userRepository;
    public UserService(UserRepositoryImpl userRepository){
        this.userRepository=userRepository;

    }

    public UserResponse addUser(CreateUserRequest request){
        UserId userId=new UserId(request.userId());
        User user=new User(userId,
                request.name(),
                request.email());
        userRepository.addUser(user);
        String id=userId.getValue();
        UserResponse response=new UserResponse(id,
                user.getName(),
                user.getEmail());
        return response;
    }

    public UserResponse getUser(String userId){
        UserId userId1=new UserId(userId);
        User user=userRepository.getUserById(userId1);
        UserResponse response=new UserResponse(userId,user.getName(),user.getEmail());
        return response;
    }

    public UserResponse updateUser(CreateUserRequest request,String userId){
        UserId userId1=new UserId(userId);
        User user1=userRepository.getUserById(userId1);
        user1.setName(request.name());
        user1.setEmail(request.email());
        userRepository.updateUser(user1,userId1);
        UserResponse response=new UserResponse(
                userId1.getValue(),
                user1.getName(),
                user1.getEmail());
        return response;
    }

    public void deleteUserById(String userId){
        UserId userId1=new UserId(userId);
        userRepository.deleteUser(userId1);
    }

}
