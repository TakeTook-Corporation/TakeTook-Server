package com.example.taketook.payload.request;

public class CreateFeedbackRequest {
    private String listingId;
    private Long starCount;
    private String text;

    public String getListingId() {
        return listingId;
    }

    public Long getStarCount() {
        return starCount;
    }

    public String getText() {
        return text;
    }
}
