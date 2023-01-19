package com.example.taketook.payload.request;

import com.example.taketook.utils.Category;

public class CreateListingRequest {
    private String title;
    private String description;
    private String author;
    private String dot;
    private Boolean active;
    private Category category;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }

    public String getDot() {
        return dot;
    }

    public Boolean getActive() {
        return active;
    }

    public Category getCategory() {
        return category;
    }
}
