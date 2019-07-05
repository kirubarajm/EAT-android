package com.tovo.eat.ui.home.region.viewmore;

import android.support.v7.widget.LinearLayoutManager;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class RegionListModule {

    DataManager dataManager;

    @Provides
    RegionListViewModel provideRegionListViewModel(DataManager dataManager) {
        this.dataManager=dataManager;
        return new RegionListViewModel(dataManager);
    }

    @Provides
    KitchenAdapter provideRegionListAdapter() {
        return new KitchenAdapter(new ArrayList<>());
    }


    @Provides
    LinearLayoutManager provideLinearLayoutManager(RegionListActivity activity) {
        return new LinearLayoutManager(activity);
    }
}
