package com.tovo.eat.ui.search;

import android.support.v7.widget.LinearLayoutManager;

import com.tovo.eat.data.DataManager;

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
    LinearLayoutManager provideLinearLayoutManager(SearchFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }
}
