package com.tovo.eat.ui.account.favorites;

import com.tovo.eat.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class FavoritesTabActivityModule {

    @Provides
    FavoritesTabActivityViewModel provideFavoritesTabViewModel(DataManager dataManager) {
        return new FavoritesTabActivityViewModel(dataManager);
    }

    @Provides
    FavoritesTabAdapter provideFavoritesTabPagerAdapter(FavoritesTabActivity mFavoritesTabActivity) {
        return new FavoritesTabAdapter(mFavoritesTabActivity.getSupportFragmentManager());
    }
}
