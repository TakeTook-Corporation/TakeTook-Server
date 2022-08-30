package com.example.taketook.payload.request.UserController;

public class CreateCommentOnUserRequest {
    private String userToRateId;
    private String text;
    private Double rating;

    public String getUserToRateId() {
        return userToRateId;
    }

    public String getText() {
        return text;
    }

    public Double getRating() {
        return rating;
    }
}
