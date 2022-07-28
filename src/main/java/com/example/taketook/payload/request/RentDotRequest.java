package com.example.taketook.payload.request;

import com.example.taketook.utils.RentTariff;

public class RentDotRequest {
    private String dotId;
    // private String automateId;
    private RentTariff rentTariff;
    private Long rentTime;

    public String getDotId() {
        return dotId;
    }

    public RentTariff getRentTariff() {
        return rentTariff;
    }

    public Long getRentTime() {
        return rentTime;
    }
}
