package com.sindhuja.twitterservice.controller;

import com.sindhuja.twitterservice.domain.UserAlreadyExistsException;
import com.sindhuja.twitterservice.domain.UserNotExistsException;
import com.sindhuja.twitterservice.dto.CreateUserRequest;
import com.sindhuja.twitterservice.domain.UserId;
import com.sindhuja.twitterservice.dto.UserResponse;
import com.sindhuja.twitterservice.dto.exception.ErrorResponse;
import com.sindhuja.twitterservice.repository.UserRepositoryImpl;
import com.sindhuja.twitterservice.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users" )
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    UserService userService;
    public UserController(UserService userService){
        this.userService=userService;
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody CreateUserRequest request){
        UserResponse response;
        try {
            response=userService.addUser(request);
        } catch (UserAlreadyExistsException ex) {
            log.error("e: ", ex);
           return new ResponseEntity<>(new ErrorResponse(ex.getMessage()),ex.getStatus());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUser(@PathVariable String id){
        UserResponse response;
        try{
            response=userService.getUser(id);
        }catch (UserNotExistsException e){
            log.error("e:",e);
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()),e.getStatus());
        }
        return ResponseEntity.ok( response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@RequestBody CreateUserRequest request,@PathVariable String id){
        UserResponse response;
        try{
            response=userService.updateUser(request,id);
        }catch(UserNotExistsException e){
            log.error("e: " +e);
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()),e.getStatus());
        }
        return ResponseEntity.ok("User updated" + response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable String id){
        try {
            userService.deleteUserById(id);
        }catch (UserNotExistsException e){
            log.error("e: "+ e);
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()),e.getStatus());
        }
        return ResponseEntity.noContent().build();
    }


}
