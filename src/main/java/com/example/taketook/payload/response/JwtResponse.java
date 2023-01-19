package com.example.taketook.payload.response;

import com.example.taketook.entity.User;

public class JwtResponse {
    private String jwtToken;
    private User user;

    public String getJwtToken() {
        return jwtToken;
    }

    public User getUser() {
        return user;
    }

    public JwtResponse(String jwtToken, User user) {
        this.jwtToken = jwtToken;
        this.user = user;
    }
}
