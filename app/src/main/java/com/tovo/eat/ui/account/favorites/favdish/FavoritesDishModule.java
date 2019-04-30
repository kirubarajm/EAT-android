package com.tovo.eat.ui.account.favorites.favdish;

import android.support.v7.widget.LinearLayoutManager;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.home.homemenu.dish.DishAdapter;
import com.tovo.eat.ui.home.homemenu.dish.DishViewModel;

import java.util.ArrayList;
import dagger.Module;
import dagger.Provides;

@Module
public class FavoritesDishModule {


    DataManager dataManager;

    @Provides
    DishViewModel provideFavDishViewModel(DataManager dataManager) {
        this.dataManager=dataManager;
        return new DishViewModel(dataManager);
    }

    @Provides
    DishAdapter provideFavDishesAdapter() {
        return new DishAdapter(new ArrayList<>(),dataManager);
    }


    @Provides
    LinearLayoutManager provideLinearLayoutManager(FavDishFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }
}
