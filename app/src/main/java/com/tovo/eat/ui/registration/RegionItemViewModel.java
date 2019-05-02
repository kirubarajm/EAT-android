package com.tovo.eat.ui.registration;

import android.databinding.ObservableField;

public class RegionItemViewModel {

    public final ObservableField<String> regionid = new ObservableField<>();
    public final ObservableField<String> regionname = new ObservableField<>();

    public RegionItemViewModel(RegionResponse.Result result) {
        this.regionid.set(String.valueOf(result.getRegionid()));
        this.regionname.set(String.valueOf(result.getRegionname()));
    }
}
