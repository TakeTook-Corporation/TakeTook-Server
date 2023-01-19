package com.example.taketook.payload.request;

import com.example.taketook.utils.RentTariff;

public class PutToSellDotRequest {
    private String dotId;
    private String listingId;
    private RentTariff rentTariff;
    private Long rentTime;

    public String getDotId() {
        return dotId;
    }

    public String getListingId() {
        return listingId;
    }

    public RentTariff getRentTariff() {
        return rentTariff;
    }

    public Long getRentTime() {
        return rentTime;
    }
}
