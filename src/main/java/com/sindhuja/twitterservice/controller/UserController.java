package com.sindhuja.twitterservice.controller;

import com.sindhuja.twitterservice.dto.CreateUserRequest;
import com.sindhuja.twitterservice.domain.UserId;
import com.sindhuja.twitterservice.dto.UserResponse;
import com.sindhuja.twitterservice.repository.UserRepositoryImpl;
import com.sindhuja.twitterservice.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users" )
public class UserController {

    UserService userService;
    public UserController(UserService userService){
        this.userService=userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest request){
        UserResponse response=userService.addUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String id){
        UserResponse response=userService.getUser(id);
        return ResponseEntity.ok( response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@RequestBody CreateUserRequest request,@PathVariable String id){
        UserResponse response=userService.updateUser(request,id);
        return ResponseEntity.ok("User updated" + response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id){
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }


}
