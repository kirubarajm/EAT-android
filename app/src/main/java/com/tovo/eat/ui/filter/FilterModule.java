package com.tovo.eat.ui.filter;



import android.support.v7.widget.LinearLayoutManager;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.home.homemenu.dish.DishAdapter;
import com.tovo.eat.ui.home.homemenu.dish.DishFragment;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class FilterModule {


    DataManager dataManager;
    @Provides
    FilterViewModel provideFilterViewModel(DataManager dataManager) {
        this.dataManager=dataManager;
        return new FilterViewModel(dataManager);
    }

    @Provides
    FilterAdapter provideFilterAdapter() {
        return new FilterAdapter(new ArrayList<>(),dataManager);
    }


    @Provides
    LinearLayoutManager provideLinearLayoutManager(FilterFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }

}
