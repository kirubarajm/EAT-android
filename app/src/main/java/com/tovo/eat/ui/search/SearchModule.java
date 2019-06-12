package com.tovo.eat.ui.search;

import android.support.v7.widget.LinearLayoutManager;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenAdapter;
import com.tovo.eat.ui.search.dish.SearchDishAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchModule {

    DataManager dataManager;

    @Provides
    SearchViewModel provideSearchViewModel(DataManager dataManager) {
        this.dataManager=dataManager;

        return new SearchViewModel(dataManager);
    }

    @Provides
    SearchAdapter provideSearchAdapter() {
        return new SearchAdapter(new ArrayList<>(),dataManager);
    }
    @Provides
    SearchDishAdapter provideSearchDishAdapter() {
        return new SearchDishAdapter(new ArrayList<>(),dataManager);
    }

    @Provides
    KitchenAdapter provideSearchRegionAdapter() {
        return new KitchenAdapter(new ArrayList<>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(SearchFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }
}
