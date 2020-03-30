package com.example.userservice;

import com.example.userservice.Controller.UserController;
import com.example.userservice.Entity.User;
import com.example.userservice.ExceptionHandling.CustomExceptions.InvalidRequestException;
import com.example.userservice.ExceptionHandling.CustomExceptions.UserNotFoundException;
import com.example.userservice.ExceptionHandling.GlobalExceptionHandler;
import com.example.userservice.Service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Before
    public void setup() {
        this.mvc = MockMvcBuilders.standaloneSetup(UserController.class)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void findAllUsers_ShouldReturnOkWithResults() throws Exception {
        List<User> expected = Arrays.asList(
                new User("First", "User", new Date(), "email@email.com", "password", "123456",
                        "123", new Date(), ""),
                new User("Second", "User", new Date(), "second@user.com", "pass1234", "987654321",
                        "789", new Date(), "Student"));

        given(userService.findAllUsers()).willReturn(new ResponseEntity<>(expected, HttpStatus.OK));

        mvc.perform(get("/user").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", is("First")))
                .andExpect(jsonPath("$[1].firstName", is("Second")));
    }

    @Test
    public void findUserById_ShouldReturnOkWithResult() throws Exception {
        User expected = new User("First", "User", new Date(), "email@email.com", "password", "123456",
                        "123", new Date(), "");
        expected.setId(1);

        given(userService.findUserById(anyInt())).willReturn(new ResponseEntity<>(expected, HttpStatus.OK));

        mvc.perform(get("/user/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(expected.getId())))
                .andExpect(jsonPath("$.firstName", is("First")));
    }

    @Test
    public void findUserById_ShouldReturn400_WhenIdIsInvalid() throws Exception {
        given(userService.findUserById(anyInt()))
                .willThrow(new InvalidRequestException("Received Id is not valid."));

        mvc.perform(get("/user/0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void findUserById_ShouldReturn404_WhenUserIsNotFound() throws Exception {
        given(userService.findUserById(anyInt()))
                .willThrow(new UserNotFoundException("User with received ID was not found."));

        mvc.perform(get("/user/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void addNewUser_ShouldReturnOkWithResult() throws Exception {
        User expected = new User("First", "User", new Date(), "email@email.com", "password", "123456",
                "123", new Date(), "");
        expected.setId(1);

        given(userService.saveUser(expected)).willReturn(new ResponseEntity<>(expected, HttpStatus.OK));

        mvc.perform(post("/user/add")
                .content(new ObjectMapper().writeValueAsString(expected))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void addNewUser_ShouldReturn400_WhenRequestIsInvalid() throws Exception {
        User user = new User(null, null, new Date(), "", "password", "123456",
                "123", new Date(), "");

        given(userService.saveUser(ArgumentMatchers.<User>any()))
                .willThrow(new InvalidRequestException("Properties that must be provided: First Name, Last Name, Email."));

        mvc.perform(post("/user/add")
                .content(new ObjectMapper().writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateUser_ShouldReturnOkWithResult() throws Exception {
        User expected = new User("First", "User", new Date(), "email@email.com", "password", "123456",
                "123", new Date(), "");
        expected.setId(1);

        given(userService.updateExistingUser(1, expected)).willReturn(new ResponseEntity<>(expected, HttpStatus.OK));

        mvc.perform(put("/user/update/1")
                .content(new ObjectMapper().writeValueAsString(expected))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updateUser_ShouldReturn400_WhenIdIsInvalid() throws Exception {
        given(userService.updateExistingUser(anyInt(), ArgumentMatchers.<User>any()))
                .willThrow(new InvalidRequestException(""));

        mvc.perform(put("/user/update/0")
                .content(new ObjectMapper().writeValueAsString(new User()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateUser_ShouldReturn404_WhenUserIsNotFound() throws Exception {
        given(userService.updateExistingUser(anyInt(), ArgumentMatchers.<User>any()))
                .willThrow(new UserNotFoundException(""));

        mvc.perform(put("/user/update/45")
                .content(new ObjectMapper().writeValueAsString(new User()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteUser_ShouldReturnOkWithResult() throws Exception {
        given(userService.deleteUser(anyInt())).willReturn(
                new ResponseEntity<>("User successfully deleted", HttpStatus.OK));

        mvc.perform(delete("/user/delete/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteUser_ShouldReturn400_WhenIdIsInvalid() throws Exception {
        given(userService.deleteUser(anyInt()))
                .willThrow(new InvalidRequestException(""));

        mvc.perform(delete("/user/delete/0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteUser_ShouldReturn404_WhenUserIsNotFound() throws Exception {
        given(userService.deleteUser(anyInt()))
                .willThrow(new UserNotFoundException(""));

        mvc.perform(delete("/user/delete/45")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
