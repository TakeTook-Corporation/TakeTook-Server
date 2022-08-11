package com.example.taketook.payload.request.ListingController;

public class CreateCommentRequest {
    private String listingId;
    private String text;
    private String authorId;

    public String getListingId() {
        return listingId;
    }

    public String getText() {
        return text;
    }

    public String getAuthorId() {
        return authorId;
    }
}
