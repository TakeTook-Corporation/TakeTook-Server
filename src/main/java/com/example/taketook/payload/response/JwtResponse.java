package com.example.taketook.payload.response;

import com.example.taketook.entity.User;

public class JwtResponse {
    private String jwt;
    private User user;

    public JwtResponse(String jwt, User user) {
        this.jwt = jwt;
        this.user = user;
    }

    public String getJwt() {
        return jwt;
    }

    public User getUser() {
        return user;
    }
}
