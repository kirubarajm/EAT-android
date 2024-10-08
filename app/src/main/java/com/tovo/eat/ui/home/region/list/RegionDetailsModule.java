package com.tovo.eat.ui.home.region.list;

import android.support.v7.widget.LinearLayoutManager;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class RegionDetailsModule {

    DataManager dataManager;

    @Provides
    RegionDetailsViewModel provideRegionListViewModel(DataManager dataManager) {
        this.dataManager=dataManager;
        return new RegionDetailsViewModel(dataManager);
    }

    @Provides
    KitchenAdapter provideRegionListAdapter() {
        return new KitchenAdapter(new ArrayList<>());
    }


    @Provides
    LinearLayoutManager provideLinearLayoutManager(RegionDetailsActivity activity) {
        return new LinearLayoutManager(activity);
    }
}
