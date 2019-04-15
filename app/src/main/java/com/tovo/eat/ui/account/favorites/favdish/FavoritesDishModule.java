package com.tovo.eat.ui.account.favorites.favdish;

import android.support.v7.widget.LinearLayoutManager;

import com.tovo.eat.data.DataManager;
import java.util.ArrayList;
import dagger.Module;
import dagger.Provides;

@Module
public class FavoritesDishModule {

    @Provides
    FavoritesDishViewModel provideFavDishViewModel(DataManager dataManager) {
        return new FavoritesDishViewModel(dataManager);
    }

    @Provides
    FavoriteDishAdapter provideFavDishesAdapter() {
        return new FavoriteDishAdapter(new ArrayList<>());
    }


    @Provides
    LinearLayoutManager provideLinearLayoutManager(FavoritesDishFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }
}
