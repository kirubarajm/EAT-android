package com.tovo.eat.ui.kitchendetails;

import android.support.v7.widget.LinearLayoutManager;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.home.kitchendish.KitchenDishAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class KitchenDetailsModule {

    DataManager dataManager;

    @Provides
    KitchenDetailsViewModel provideKitchenDishViewModel(DataManager dataManager) {
        this.dataManager=dataManager;

        return new KitchenDetailsViewModel(dataManager);
    }

    @Provides
    KitchenDishAdapter provideKitchenDishAdapter() {
        return new KitchenDishAdapter(new ArrayList<>(),dataManager);
    }


    @Provides
    InfoImageAdapter provideInfoImageAdapter() {
        return new InfoImageAdapter(new ArrayList<>(),dataManager);
    }

    @Provides
    MenuKitchenInfoImageAdapter provideKitchenCommonImageAdapter() {
        return new MenuKitchenInfoImageAdapter(new ArrayList<>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(KitchenDetailsActivity activity) {
        return new LinearLayoutManager(activity);
    }
}
