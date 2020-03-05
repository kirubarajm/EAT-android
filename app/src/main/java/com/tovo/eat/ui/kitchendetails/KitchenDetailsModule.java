package com.tovo.eat.ui.kitchendetails;

import android.support.v7.widget.LinearLayoutManager;

import com.tovo.eat.data.DataManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class KitchenDetailsModule {

    DataManager dataManager;

    @Provides
    KitchenDetailsViewModel provideKitchenDishViewModel(DataManager dataManager) {
        this.dataManager = dataManager;

        return new KitchenDetailsViewModel(dataManager);
    }

    @Provides
    KitchenProductsAdapter provideFavTodaysMenuAdapter() {
        return new KitchenProductsAdapter(new ArrayList<>(),dataManager);
    }
 @Provides
 KitchenHeaderAdapter provideKitchenHeaderAdapter() {
        return new KitchenHeaderAdapter(new ArrayList<>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(KitchenDetailsActivity activity) {
        return new LinearLayoutManager(activity);
    }
}
