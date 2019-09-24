package com.tovo.eat.ui.account.favorites;

import com.tovo.eat.data.DataManager;

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
