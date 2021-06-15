
package com.example.skincare.foursquareAPI.modules;

import java.util.List;
import com.google.gson.annotations.SerializedName;



public class Venue {

    @SerializedName("categories")
    private List<Category> mCategories;
    @SerializedName("hasPerk")
    private Boolean mHasPerk;
    @SerializedName("id")
    private String mId;
    @SerializedName("location")
    private Location mLocation;
    @SerializedName("name")
    private String mName;
    @SerializedName("referralId")
    private String mReferralId;

    public List<Category> getCategories() {
        return mCategories;
    }

    public void setCategories(List<Category> categories) {
        mCategories = categories;
    }

    public Boolean getHasPerk() {
        return mHasPerk;
    }

    public void setHasPerk(Boolean hasPerk) {
        mHasPerk = hasPerk;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public Location getLocation() {
        return mLocation;
    }

    public void setLocation(Location location) {
        mLocation = location;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getReferralId() {
        return mReferralId;
    }

    public void setReferralId(String referralId) {
        mReferralId = referralId;
    }

}
