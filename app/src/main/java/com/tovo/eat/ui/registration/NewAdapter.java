package com.tovo.eat.ui.registration;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

public class NewAdapter extends ArrayAdapter<RegionResponse.Result> {


    public String regionName;
    public String regionId;

    public NewAdapter(Context context, int resource, String regionName, String regionId) {
        super(context, resource);
        this.regionName = regionName;
        this.regionId = regionId;
    }

    public NewAdapter(Context context, int resource, int textViewResourceId, String regionName, String regionId) {
        super(context, resource, textViewResourceId);
        this.regionName = regionName;
        this.regionId = regionId;
    }

    public NewAdapter(Context context, int resource, RegionResponse.Result[] objects, String regionName, String regionId) {
        super(context, resource, objects);
        this.regionName = regionName;
        this.regionId = regionId;
    }

    public NewAdapter(Context context, int resource, int textViewResourceId, RegionResponse.Result[] objects, String regionName, String regionId) {
        super(context, resource, textViewResourceId, objects);
        this.regionName = regionName;
        this.regionId = regionId;
    }

    public NewAdapter(Context context, int resource, List<RegionResponse.Result> objects, String regionName, String regionId) {
        super(context, resource, objects);
        this.regionName = regionName;
        this.regionId = regionId;
    }

    public NewAdapter(Context context, int resource, int textViewResourceId, List<RegionResponse.Result> objects, String regionName, String regionId) {
        super(context, resource, textViewResourceId, objects);
        this.regionName = regionName;
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }
}
