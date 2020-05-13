package com.example.authenticationservice.model;

import javax.persistence.Column;

public class User {
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;
    public User(){

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
