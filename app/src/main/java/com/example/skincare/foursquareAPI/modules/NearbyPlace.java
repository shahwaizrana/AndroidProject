
package com.example.skincare.foursquareAPI.modules;

import com.google.gson.annotations.SerializedName;

public class NearbyPlace {

    @SerializedName("meta")
    private Meta mMeta;
    @SerializedName("response")
    private Response mResponse;

    public Meta getMeta() {
        return mMeta;
    }

    public void setMeta(Meta meta) {
        mMeta = meta;
    }

    public Response getResponse() {
        return mResponse;
    }

    public void setResponse(Response response) {
        mResponse = response;
    }

}
