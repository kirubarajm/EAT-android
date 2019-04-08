package com.tovo.eat.ui.address.select;

import android.support.v7.widget.LinearLayoutManager;

import com.tovo.eat.data.DataManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class SelectAddressListModule {

    DataManager dataManager;

    @Provides
    SelectAddressListViewModel provideAddressListViewModel(DataManager dataManager) {
        this.dataManager=dataManager;
        return new SelectAddressListViewModel(dataManager);
    }

    @Provides
    SelectAddressListAdapter provideAddressListAdapter() {
        return new SelectAddressListAdapter(new ArrayList<>());
    }


    @Provides
    LinearLayoutManager provideLinearLayoutManager(SelectSelectAddressListActivity activity) {
        return new LinearLayoutManager(activity);
    }
}
