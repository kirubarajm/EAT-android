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
    @SerializedName("eatuserid")
    @Expose
    private Integer eatuserid;


    public LatLngPojo(String lat, String lon, Integer eatuserid) {
        this.lat = lat;
        this.lon = lon;
        this.eatuserid = eatuserid;
    }


}



