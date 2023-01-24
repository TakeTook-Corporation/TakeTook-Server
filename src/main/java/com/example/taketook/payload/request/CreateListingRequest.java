package com.example.taketook.payload.request;

import com.example.taketook.utils.Category;

public class CreateListingRequest {

    private String title;
    private Integer price;
    private String city;
    private Boolean usingAutomate;
    private String description;
    private String dot;
    private Category category;

    public String getTitle() {
        return title;
    }

    public Integer getPrice() {
        return price;
    }

    public String getCity() {
        return city;
    }

    public Boolean getUsingAutomate() {
        return usingAutomate;
    }

    public String getDescription() {
        return description;
    }

    public String getDot() {
        return dot;
    }

    public Category getCategory() {
        return category;
    }
}
