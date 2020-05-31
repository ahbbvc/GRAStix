package com.example.routeservice;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public  void configure(HttpSecurity http) throws Exception{
        http    //.authorizeRequests().anyRequest().permitAll();
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/**").hasAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.DELETE, "/**").hasAuthority("ROLE_ADMIN")
                .anyRequest().permitAll();
    }
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        // do not require a resource id in AccessToken.
        resources.resourceId(null);
    }
}
