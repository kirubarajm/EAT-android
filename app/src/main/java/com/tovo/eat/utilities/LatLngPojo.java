package com.tovo.eat.utilities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LatLngPojo {

    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lon")
    @Expose
    private String lon;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }


    public LatLngPojo(String lat, String lon) {
        this.lat = lat;
        this.lon = lon;
    }
}
