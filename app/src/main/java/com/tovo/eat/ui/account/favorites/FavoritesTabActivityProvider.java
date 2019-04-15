package com.tovo.eat.ui.account.favorites;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FavoritesTabActivityProvider {

    @ContributesAndroidInjector(modules = FavoritesTabActivityModule.class)
    abstract FavoritesTabActivity provideFavoritesTabFragmentFactory();
}
