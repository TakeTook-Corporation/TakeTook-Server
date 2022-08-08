package com.example.taketook.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Story {
    @Id
    private String id;

    private String iconUrl;
    private String infoIconUrl;

    public Story() {}

    public Story(String iconUrl, String infoIconUrl) {
        this.iconUrl = iconUrl;
        this.infoIconUrl = infoIconUrl;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getInfoIconUrl() {
        return infoIconUrl;
    }

    public void setInfoIconUrl(String infoIconUrl) {
        this.infoIconUrl = infoIconUrl;
    }
}
