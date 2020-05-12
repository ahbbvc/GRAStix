package com.example.ticketservice.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

public class TicketServiceInterceptorAppConfig extends WebMvcConfigurerAdapter {
    @Autowired
    TicketServiceInterceptor ticketServiceInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(ticketServiceInterceptor);
    }
}
