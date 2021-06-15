
package com.example.skincare.foursquareAPI.modules;

import com.google.gson.annotations.SerializedName;

public class LabeledLatLng {

    @SerializedName("label")
    private String mLabel;
    @SerializedName("lat")
    private Double mLat;
    @SerializedName("lng")
    private Double mLng;

    public String getLabel() {
        return mLabel;
    }

    public void setLabel(String label) {
        mLabel = label;
    }

    public Double getLat() {
        return mLat;
    }

    public void setLat(Double lat) {
        mLat = lat;
    }

    public Double getLng() {
        return mLng;
    }

    public void setLng(Double lng) {
        mLng = lng;
    }

}
