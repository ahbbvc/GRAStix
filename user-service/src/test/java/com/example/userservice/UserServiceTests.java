package com.example.userservice;

import com.example.userservice.Entity.User;
import com.example.userservice.Repository.UserRepository;
import com.example.userservice.Service.UserService;
import com.example.userservice.Service.ValidationService;
import com.flextrade.jfixture.JFixture;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

public class UserServiceTests {
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder pwEncoder;

    private ValidationService validationService;
    private UserService userService;
    private JFixture fixture;

    UserServiceTests() {
        userRepository = mock(UserRepository.class);
        pwEncoder = mock(PasswordEncoder.class);
        validationService = new ValidationService();
        userService = new UserService(userRepository, validationService, pwEncoder);
        fixture = new JFixture();
    }

    @Test
    public void findAllUsersTest_ShouldReturnOkWithResults() {
        List<User> expected = Arrays.asList(
                new User("First", "User", new Date(), "email@email.com", "password", "123456",
                        "123", new Date(), ""),
                new User("Second", "User", new Date(), "second@user.com", "pass1234", "987654321",
                        "789", new Date(), "Student"));

        given(userRepository.findAll()).willReturn(expected);

        ResponseEntity<List<User>> actual = userService.findAllUsers();

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(2, actual.getBody().size());
    }

    @Test
    public void findUserByIdTest_ShouldReturnOkWithResult() {
        User expected = new User("First", "User", new Date(), "email@email.com", "password", "123456",
                "123", new Date(), "");

        given(userRepository.findById(anyInt())).willReturn(Optional.of(expected));

        ResponseEntity<User> actual = null;
        try {
            actual = userService.findUserById(5);
        } catch (Exception e) {}

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertNotNull(actual.getBody());
    }

    @Test
    public void saveUserTest_ShouldReturnOkWithResult() {
        User expected = new User("First", "User", new Date(), "email@email.com", "password", "123456",
                "123", new Date(), "");

        given(pwEncoder.encode("password")).willReturn("password");
        given(userRepository.save(expected)).willReturn(expected);

        ResponseEntity<User> actual = null;
        try {
             actual = userService.saveUser(expected);
        } catch (Exception e) {}

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertNotNull(actual.getBody());
    }

    @Test
    public void updateExistingUserTest_ShouldReturnOkWithResult() {
        User expected = new User("First", "User", new Date(), "email@email.com", "password", "123456",
                "123", new Date(), "");

        given(pwEncoder.encode("password")).willReturn("password");
        given(userRepository.save(expected)).willReturn(expected);
        given(userRepository.findById(anyInt())).willReturn(Optional.of(expected));

        ResponseEntity<User> actual = null;
        try {
            actual = userService.updateExistingUser(5, expected);
        } catch (Exception e) {}

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertNotNull(actual.getBody());
    }

    @Test
    public void deleteUserTest_ShouldReturnOkWithResult() {
        User expected = new User("First", "User", new Date(), "email@email.com", "password", "123456",
                "123", new Date(), "");
        given(userRepository.findById(anyInt())).willReturn(Optional.of(expected));
        doNothing().when(userRepository).deleteById(anyInt());

        ResponseEntity<String> actual = null;
        try {
            actual = userService.deleteUser(5);
        } catch (Exception e) {}

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals("User successfully deleted.", actual.getBody());
    }
}
