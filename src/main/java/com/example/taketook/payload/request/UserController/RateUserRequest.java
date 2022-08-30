package com.example.taketook.payload.request.UserController;

public class RateUserRequest {
    private String userToRateId;
    private Double rating;

    public String getUserToRateId() {
        return userToRateId;
    }

    public Double getRating() {
        return rating;
    }
}
