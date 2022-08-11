package com.example.taketook.payload.request.UserController;

public class RateUserRequest {
    private String userToRateId;
    private String authorId;
    private Double rating;

    public String getUserToRateId() {
        return userToRateId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public Double getRating() {
        return rating;
    }
}
