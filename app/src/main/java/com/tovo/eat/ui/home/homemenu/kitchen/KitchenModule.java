package com.tovo.eat.ui.home.homemenu.kitchen;

import android.support.v7.widget.LinearLayoutManager;


import com.tovo.eat.data.DataManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class KitchenModule {

    @Provides
    KitchenViewModel provideKitchenViewModel(DataManager dataManager) {
        return new KitchenViewModel(dataManager);
    }

    @Provides
    KitchenAdapter provideKitchenAdapter() {
        return new KitchenAdapter(new ArrayList<>());
    }


    @Provides
    LinearLayoutManager provideLinearLayoutManager(KitchenFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }
}
