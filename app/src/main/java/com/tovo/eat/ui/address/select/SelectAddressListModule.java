package com.tovo.eat.ui.address.select;

import android.support.v7.widget.LinearLayoutManager;

import com.tovo.eat.data.DataManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class SelectAddressListModule {



    @Provides
    SelectAddressListViewModel provideSelectAddressListViewModel(DataManager dataManager) {
        return new SelectAddressListViewModel(dataManager);
    }

    @Provides
    SelectAddressListAdapter provideSelectAddressListAdapter() {
        return new SelectAddressListAdapter(new ArrayList<>());
    }


    @Provides
    LinearLayoutManager provideLinearLayoutManager(SelectSelectAddressListActivity activity) {
        return new LinearLayoutManager(activity);
    }
}
