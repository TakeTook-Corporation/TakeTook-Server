package com.example.taketook.payload.request;

public class CreateCommentOnUserRequest {
    private String authorId;
    private String text;

    public String getAuthorId() {
        return authorId;
    }

    public String getText() {
        return text;
    }
}
