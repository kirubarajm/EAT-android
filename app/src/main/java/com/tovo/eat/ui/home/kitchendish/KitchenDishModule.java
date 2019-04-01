package com.tovo.eat.ui.home.kitchendish;

import android.support.v7.widget.LinearLayoutManager;

import com.tovo.eat.data.DataManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class KitchenDishModule {

    DataManager dataManager;

    @Provides
    KitchenDishViewModel provideKitchenDishViewModel(DataManager dataManager) {
        this.dataManager=dataManager;

        return new KitchenDishViewModel(dataManager);
    }

    @Provides
    KitchenDishAdapter provideKitchenDishAdapter() {
        return new KitchenDishAdapter(new ArrayList<>(),dataManager);
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(KitchenDishActivity activity) {
        return new LinearLayoutManager(activity);
    }
}
