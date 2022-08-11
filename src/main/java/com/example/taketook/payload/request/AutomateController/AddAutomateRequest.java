package com.example.taketook.payload.request.AutomateController;

import java.util.List;

public class AddAutomateRequest {
    private Double lat;
    private Double lon;
    private String address;
    private Long dotCount;

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }

    public String getAddress() {
        return address;
    }

    public Long getDotCount() {
        return dotCount;
    }
}
