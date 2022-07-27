package com.example.taketook.entity;

import com.example.taketook.utils.Category;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Listing {
    @Id
    private Long id;

    private String title;
    private String description;
    // private User author;
    private Long dot_id;
    private Boolean active;
    private Category category;

    public Listing() {}

    public Listing(String title, String description, Long dot_id, Boolean active, Category category) {
        this.title = title;
        this.description = description;
        this.dot_id = dot_id;
        this.active = active;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDot_id() {
        return dot_id;
    }

    public void setDot_id(Long dot_id) {
        this.dot_id = dot_id;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
