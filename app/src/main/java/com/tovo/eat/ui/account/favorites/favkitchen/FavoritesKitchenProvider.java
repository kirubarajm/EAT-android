package com.tovo.eat.ui.account.favorites.favkitchen;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FavoritesKitchenProvider {

    @ContributesAndroidInjector(modules = FavoritesKitchenModule.class)
    abstract FavoritesKitchenFragment provideFavKitchenFragmentFactory();
}
