package com.tovo.eat.ui.account.favorites.favkitchen;


import android.support.v7.widget.LinearLayoutManager;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenAdapter;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenViewModel;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class FavoritesKitchenModule {

    @Provides
    FavKitchenViewModel provideFavKitchenViewModel(DataManager dataManager) {
        return new FavKitchenViewModel(dataManager);
    }

    @Provides
    FavKitchenAdapter provideFavKitchenAdapter() {
        return new FavKitchenAdapter(new ArrayList<>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(FavKitchenFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }

}
