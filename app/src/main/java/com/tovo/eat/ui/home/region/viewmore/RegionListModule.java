package com.tovo.eat.ui.home.region.viewmore;

import android.support.v7.widget.LinearLayoutManager;

import com.tovo.eat.data.DataManager;

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
    RegionsListAdapter provideRegionListAdapter() {
        return new RegionsListAdapter(new ArrayList<>());
    }


    @Provides
    LinearLayoutManager provideLinearLayoutManager(RegionListActivity activity) {
        return new LinearLayoutManager(activity);
    }
}
