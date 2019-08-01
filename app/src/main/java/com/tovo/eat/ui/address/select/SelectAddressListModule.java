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
    SelectAddressListViewModel provideSelectAddressListViewModel(DataManager dataManager) {
    this.dataManager=dataManager;
        return new SelectAddressListViewModel(dataManager);
    }

    @Provides
    SelectAddressListAdapter provideSelectAddressListAdapter() {
        return new SelectAddressListAdapter(new ArrayList<>(),dataManager);
    }


    @Provides
    LinearLayoutManager provideLinearLayoutManager(SelectAddressListActivity activity) {
        return new LinearLayoutManager(activity);
    }
}
