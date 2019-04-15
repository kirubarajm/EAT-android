package com.tovo.eat.ui.account.favorites.favkitchen;





import com.tovo.eat.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class FavoritesKitchenModule {

    @Provides
    FavoritesKitchenViewModel provideFavKitchenViewModel(DataManager dataManager) {
        return new FavoritesKitchenViewModel(dataManager);
    }
}
