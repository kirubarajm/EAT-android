package com.tovo.eat.ui.address.list;

import android.support.v7.widget.LinearLayoutManager;

import com.tovo.eat.data.DataManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class AddressListModule {

    DataManager dataManager;

    @Provides
    AddressListViewModel provideAddressListViewModel(DataManager dataManager) {
        this.dataManager=dataManager;
        return new AddressListViewModel(dataManager);
    }

    @Provides
    AddressListAdapter provideAddressListAdapter() {
        return new AddressListAdapter(new ArrayList<>());
    }


    @Provides
    LinearLayoutManager provideLinearLayoutManager(AddressListActivity activity) {
        return new LinearLayoutManager(activity);
    }
}
