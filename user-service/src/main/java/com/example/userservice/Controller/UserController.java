package com.example.userservice.Controller;

import java.util.Date;
import java.util.List;

import com.example.grpc.SystemEventsRequest;
import com.example.grpc.SystemEventsServiceGrpc;
import com.example.userservice.Entity.User;
import com.example.userservice.ExceptionHandling.CustomExceptions.InvalidRequestException;
import com.example.userservice.ExceptionHandling.CustomExceptions.UserNotFoundException;
import com.example.userservice.Service.UserService;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService _userService;

    public UserController(UserService userService) {
        this._userService = userService;
    }

   ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost",9090 ).usePlaintext().build();

    SystemEventsServiceGrpc.SystemEventsServiceBlockingStub stub1 = SystemEventsServiceGrpc.newBlockingStub(channel);

    @GetMapping("")
    ResponseEntity<List<User>> findAllUsers() {
        ResponseEntity<List<User>> res =  _userService.findAllUsers();

        stub1.getAction(SystemEventsRequest.newBuilder()
                .setMicroservice("user-service")
                .setAction("GET")
                .setResponse(res.getStatusCode().toString())
                .setResource("user")
                .setTimeStamp(new Date().toString())
                .build());

        return res;
    }

    @GetMapping("/{id}")
    ResponseEntity<User> findUserById(@PathVariable(value="id") Integer id) throws InvalidRequestException, UserNotFoundException {
        return this._userService.findUserById(id);
    }
    @PostMapping("/add")
    ResponseEntity<User> addNewUser(@RequestBody User user) throws InvalidRequestException {
        return _userService.saveUser(user);
    }

    @PutMapping("/update/{id}")
    ResponseEntity<User> updateUser(@PathVariable(value="id") Integer id, @RequestBody User user) throws InvalidRequestException, UserNotFoundException {
        return this._userService.updateExistingUser(id, user);
    }

    @DeleteMapping("delete/{id}")
    ResponseEntity deleteUser(@PathVariable(value="id") Integer id) throws InvalidRequestException, UserNotFoundException {
        return this._userService.deleteUser(id);
    }

    @GetMapping(value ="", params = "email")
    public ResponseEntity<User> UserByMail(@RequestParam("email") String email){
        return this._userService.findUserByEmil(email);
    }


}
