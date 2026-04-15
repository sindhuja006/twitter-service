package com.sindhuja.twitterservice.controller;

import com.sindhuja.twitterservice.domain.UserId;
import com.sindhuja.twitterservice.dto.CreateUpdateUserRequest;
import com.sindhuja.twitterservice.dto.CreateUserRequest;
import com.sindhuja.twitterservice.dto.UserResponse;
import com.sindhuja.twitterservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {

    MockMvc mockMvc;
    UserService userService;

    @BeforeEach
    void setUp(){
        userService=mock();
        mockMvc= MockMvcBuilders.standaloneSetup(new UserController(userService)).build();
    }

    @Test
    void createUserTest() throws Exception {
            CreateUserRequest request=new CreateUserRequest("1","sindhuja","sindhuja@gmail.com");
            UserResponse response=new UserResponse(request.userId(),request.name(),request.email());
            when(userService.addUser(request)).thenReturn(response);
            mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "userId" : "1",
                          "name" : "sindhuja",
                          "email" : "sindhuja@gmail.com"
                        }
                        """.trim()))
                    .andExpect(status().isCreated())
                    .andDo(print())
                .andExpect(jsonPath("$.userId").value("1"))
                .andExpect(jsonPath("$.name").value("sindhuja"))
                .andExpect(jsonPath("$.email").value("sindhuja@gmail.com"));
    }

    @Test
    void getUserTest() throws Exception {
        String id="1";
        UserId userId=new UserId(id);
        UserResponse userResponse=new UserResponse(id,"sindhu","sindhu@gmail.com");
        when(userService.getUser(id)).thenReturn(userResponse);
        mockMvc.perform(get("/users/{id}","1").contentType(MediaType.APPLICATION_JSON)
                .content("""
                      {
                         "userId": "1",
                         "name" :"sindhu",
                         "email" :"sindhu@gmail.com"                   
                      }
                      """.trim())).andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value("1"))
                .andExpect(jsonPath("$.name").value("sindhu"))
                .andExpect(jsonPath("$.email").value("sindhu@gmail.com"));

    }

    @Test
    void updateUserTest() throws Exception {
        String id="1";
        CreateUpdateUserRequest request=new CreateUpdateUserRequest("sindhu","sindhu@gmail.com");
        UserResponse response=new UserResponse(id,request.name(),request.email());
        when(userService.updateUser(request,id)).thenReturn(response);
        mockMvc.perform(put("/users/{id}","1").contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                           "userId" : "1",
                           "name": "sindhu",
                           "email": "sindhu@gmail.com"
                         }
                        """.trim())).andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("sindhu"))
                .andExpect(jsonPath("$.email").value("sindhu@gmail.com"));
    }

    @Test
    void deleteUserTest() throws Exception {
        String id="1";
        doNothing().when(userService).deleteUserById(id);
        mockMvc.perform(delete("/users/{id}","1")).andExpect(status().isNoContent());
    }
}
