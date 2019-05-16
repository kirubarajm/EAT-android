package com.tovo.eat.ui.home.region;

import android.support.v7.widget.LinearLayoutManager;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.registration.RegionAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class RegionModule {

    @Provides
    RegionViewModel provideRegionViewModel(DataManager dataManager) {
        return new RegionViewModel(dataManager);
    }

    @Provides
    RegionsAdapter provideKitchenAdapter() {
        return new RegionsAdapter(new ArrayList<>());
    }

/*
    @Provides
    RegionKitchenAdapter provideRegionKitchenAdapter() {
        return new RegionKitchenAdapter(new ArrayList<>());
    }*/


    @Provides
    LinearLayoutManager provideLinearLayoutManager(RegionFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }
}
