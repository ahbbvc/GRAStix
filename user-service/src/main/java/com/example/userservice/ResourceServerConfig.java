package com.example.userservice;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public  void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                .antMatchers("/user/1").authenticated()
                .antMatchers("/**").permitAll();
    }
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        // do not require a resource id in AccessToken.
        resources.resourceId(null);
    }
}