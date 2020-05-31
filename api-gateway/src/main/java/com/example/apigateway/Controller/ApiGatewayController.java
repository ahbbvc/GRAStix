package com.example.apigateway.Controller;


import com.example.apigateway.Model.Login;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Base64;

@RestController
public class ApiGatewayController {
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login login){
        RestTemplate restTemplate = new RestTemplate();

        // According OAuth documentation we need to send the client id and secret key in the header for authentication
        String credentials = "grastix:secretkey";
        String encodedCredentials = new String(Base64.getEncoder().encode(credentials.getBytes()));
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();

        body.add("grant_type", "password");
        body.add("scope", "webclient");
        body.add("username", login.getEmail());
        body.add("password", login.getPassword());
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Basic " + encodedCredentials);
        HttpEntity<?> request = new HttpEntity<Object>(body, headers);

        String access_token_url = "http://localhost:8090/oauth/token";
        ResponseEntity<?>response = restTemplate.exchange(access_token_url, HttpMethod.POST, request, String.class);

        return response;
    }
}
