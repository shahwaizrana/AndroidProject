
package com.example.skincare.foursquareAPI.modules;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("venues")
    private List<Venue> mVenues;

    public List<Venue> getVenues() {
        return mVenues;
    }

    public void setVenues(List<Venue> venues) {
        mVenues = venues;
    }

}
