package com.tovo.eat.ui.account.favorites;

import android.support.v7.widget.LinearLayoutManager;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.home.homemenu.dish.DishAdapter;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenAdapter;
import com.tovo.eat.ui.home.kitchendish.KitchenDishAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class FavouritesModule {

    DataManager dataManager;

    @Provides
    FavouritesViewModel provideFavouritesViewModel(DataManager dataManager) {
        this.dataManager = dataManager;

        return new FavouritesViewModel(dataManager);
    }

}
