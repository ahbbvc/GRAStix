package com.example.apigateway;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public  void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/routes/**").hasAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.DELETE, "/routes/**").hasAuthority("ROLE_ADMIN")
                .antMatchers( "/routes/**").hasAnyAuthority("ROLE_ADMIN","ROLE_USER")
                .antMatchers( "/tickets/**").hasAnyAuthority("ROLE_ADMIN","ROLE_USER")
                .antMatchers(HttpMethod.POST, "/users/user/add").permitAll()
                .antMatchers( "/users/**").hasAnyAuthority("ROLE_ADMIN","ROLE_USER")
                .antMatchers("/**").permitAll();
    }
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(null);
    }
}
