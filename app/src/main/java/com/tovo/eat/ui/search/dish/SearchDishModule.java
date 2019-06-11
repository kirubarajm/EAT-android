 package com.tovo.eat.ui.search.dish;

import android.support.v7.widget.LinearLayoutManager;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.home.homemenu.dish.DishAdapter;
import com.tovo.eat.ui.home.homemenu.dish.DishFragment;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchDishModule {

    DataManager dataManager;

    @Provides
    SearchDishViewModel provideSearchDishViewModel(DataManager dataManager) {
        this.dataManager=dataManager;

        return new SearchDishViewModel(dataManager);
    }

    @Provides
    SearchDishAdapter provideDishAdapter() {
        return new SearchDishAdapter(new ArrayList<>(),dataManager);
    }


    @Provides
    LinearLayoutManager provideLinearLayoutManager(SearchDishActivity activity) {
        return new LinearLayoutManager(activity);
    }
}
