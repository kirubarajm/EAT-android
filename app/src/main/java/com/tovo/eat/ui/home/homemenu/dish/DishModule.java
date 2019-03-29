package com.tovo.eat.ui.home.homemenu.dish;

import android.support.v7.widget.LinearLayoutManager;

import com.tovo.eat.data.DataManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class DishModule {

    DataManager dataManager;

    @Provides
    DishViewModel provideDishViewModel(DataManager dataManager) {
        this.dataManager=dataManager;

        return new DishViewModel(dataManager);
    }

    @Provides
    DishAdapter provideDishAdapter() {
        return new DishAdapter(new ArrayList<>(),dataManager);
    }


    @Provides
    LinearLayoutManager provideLinearLayoutManager(DishFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }
}
