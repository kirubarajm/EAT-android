package com.tovo.eat.ui.account.favorites.favdish;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FavoritesDishProvider {

    @ContributesAndroidInjector(modules = FavoritesDishModule.class)
    abstract FavoritesDishFragment provideFavDishFragmentFactory();
}
