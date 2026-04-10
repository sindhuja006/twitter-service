package com.sindhuja.twitterservice.Service;

import com.sindhuja.twitterservice.domain.User;
import com.sindhuja.twitterservice.domain.UserId;
import com.sindhuja.twitterservice.dto.CreateUpdateUserRequest;
import com.sindhuja.twitterservice.dto.CreateUserRequest;
import com.sindhuja.twitterservice.dto.UserResponse;
import com.sindhuja.twitterservice.repository.IUserRepository;
import com.sindhuja.twitterservice.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {

    UserService userService;
    IUserRepository userRepository;

    @BeforeAll
    public void sut() {
        userRepository = mock();
        userService = new UserService(userRepository);
    }


    @Test
    public void addUserTest() {
        CreateUserRequest request = new CreateUserRequest("1", "sindhu", "sindhuja@gmail.com");
        UserId userId = new UserId(request.userId());
        User user = new User(userId, request.name(), request.email());
        doNothing().when(userRepository).verifyUserNotExists(userId);
        when(userRepository.addUser(user)).thenReturn(user);
        UserResponse actual = userService.addUser(request);
        Assertions.assertEquals(request.userId(), actual.userId());
        Assertions.assertEquals(request.name(), actual.name());
        Assertions.assertEquals(request.email(), actual.email());
    }

    @Test
    public void getUserTest(){
        String id="1";
        UserId userId=new UserId(id);
        User user=new User(userId,"sindhu","sindhu@gmail.com");
        doNothing().when(userRepository).verifyUserExists(userId);
        when(userRepository.getUserById(userId)).thenReturn(user);
        UserResponse actual=userService.getUser(id);
        Assertions.assertEquals(id,actual.userId());
        Assertions.assertEquals(user.getName(),actual.name());
        Assertions.assertEquals(user.getEmail(),actual.email());
    }

    @Test
    public void updateUserTest(){
        String id="1";
        UserId userId=new UserId(id);
        CreateUpdateUserRequest request= new CreateUpdateUserRequest("sindhu","sindhu@gmail.com");
        User user=new User(userId,request.name(),request.email());
        doNothing().when(userRepository).verifyUserExists(userId);
        when(userRepository.getUserById(userId)).thenReturn(user);
        when(userRepository.updateUser(user,userId)).thenReturn(user);
        UserResponse actual=userService.updateUser(request,id);
        Assertions.assertEquals(request.name(),actual.name());
        Assertions.assertEquals(request.email(),actual.email());
    }

    @Test
    public void deleteUserTest(){
        String id="1";
        UserId userId=new UserId(id);
        doNothing().when(userRepository).verifyUserExists(userId);
        doNothing().when(userRepository).deleteUser(userId);
    }

}
