package com.tovo.eat.ui.search.dish;

import com.tovo.eat.ui.home.homemenu.dish.DishFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class SearchDishProvider {

    @ContributesAndroidInjector(modules = SearchDishModule.class)
    abstract DishFragment provideDishFragmentFactory();
}
