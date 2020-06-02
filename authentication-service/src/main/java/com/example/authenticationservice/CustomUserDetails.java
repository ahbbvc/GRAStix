package com.example.authenticationservice;

import com.example.authenticationservice.model.MyUserPrincipal;
import com.example.authenticationservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomUserDetails implements UserDetailsService {
    @Autowired
    RestTemplate restTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = restTemplate.getForObject("http://user-service/user/?email=" + username, User.class);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        user.setPassword("{noop}" + user.getPassword());
        return new MyUserPrincipal(user);

    }

   /* public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }*/
}
