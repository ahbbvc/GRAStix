package com.example.routeservice.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
public class RouteServiceInterceptorAppConfig extends WebMvcConfigurerAdapter {
    @Autowired
    RouteServiceInterceptor routeServiceInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(routeServiceInterceptor);
    }
}
