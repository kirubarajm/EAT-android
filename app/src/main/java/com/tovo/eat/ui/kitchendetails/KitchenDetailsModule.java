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
    FavoriteAdapter provideFavTodaysMenuAdapter() {
        return new FavoriteAdapter(new ArrayList<>(), dataManager);
    }

    @Provides
    TodaysMenuAdapter provideTodaysMenuAdapter() {
        return new TodaysMenuAdapter(new ArrayList<>(), dataManager);
    }

    @Provides
    FoodBadgeAdapter provideFoodBadgesImageAdapter() {
        return new FoodBadgeAdapter(new ArrayList<>(), dataManager);
    }

    @Provides
    SpecialitiesAdapter provideSpecialitiesImageAdapter() {
        return new SpecialitiesAdapter(new ArrayList<>(), dataManager);
    }

    @Provides
    MenuKitchenInfoCommonImageAdapter provideKitchenCommonImageAdapter() {
        return new MenuKitchenInfoCommonImageAdapter(new ArrayList<>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(KitchenDetailsActivity activity) {
        return new LinearLayoutManager(activity);
    }
}
