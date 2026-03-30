package com.sindhuja.twitterservice.service;

import com.sindhuja.twitterservice.dto.CreateUpdateUserRequest;
import com.sindhuja.twitterservice.dto.CreateUserRequest;
import com.sindhuja.twitterservice.domain.User;
import com.sindhuja.twitterservice.domain.UserId;
import com.sindhuja.twitterservice.dto.UserResponse;
import com.sindhuja.twitterservice.repository.IUserRepository;
import com.sindhuja.twitterservice.repository.UserRepositoryImpl;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    IUserRepository userRepository;
    public UserService(IUserRepository userRepository){
        this.userRepository=userRepository;

    }

    public UserResponse addUser(CreateUserRequest request){
        UserId userId=new UserId(request.userId());
        userRepository.verifyUserNotExists(userId);
        User user=new User(userId,
                request.name(),
                request.email());
        User savedUser=userRepository.addUser(user);
        return new UserResponse(savedUser.getUserId().getUserValue(),
                savedUser.getName(),
                savedUser.getEmail());
    }

    public UserResponse getUser(String userId){
        UserId userId1=new UserId(userId);
        userRepository.verifyUserExists(userId1);
        User user=userRepository.getUserById(userId1);
        return new UserResponse(userId,user.getName(),user.getEmail());
    }

    public UserResponse updateUser(CreateUpdateUserRequest request, String userId){
        UserId userId1=new UserId(userId);
        userRepository.verifyUserExists(userId1);
        User user1=userRepository.getUserById(userId1);
        user1.setName(request.name());
        user1.setEmail(request.email());
        User updatedUser=userRepository.updateUser(user1,userId1);
        return new UserResponse(
                updatedUser.getUserId().getUserValue(),
                updatedUser.getName(),
                updatedUser.getEmail());
    }

    public void deleteUserById(String userId){
        UserId userId1=new UserId(userId);
        userRepository.verifyUserExists(userId1);
        userRepository.deleteUser(userId1);
    }

}
