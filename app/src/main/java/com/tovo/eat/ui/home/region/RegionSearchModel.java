package com.tovo.eat.ui.home.region;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegionSearchModel {

    @SerializedName("regions")
    @Expose
    private String regions;

    @Override
    public String toString() {
        return regions;
    }

    public RegionSearchModel(String regions) {
        this.regions = regions;
    }

    public String getRegions() {
        return regions;
    }

    public void setRegions(String regions) {
        this.regions = regions;
    }
}
