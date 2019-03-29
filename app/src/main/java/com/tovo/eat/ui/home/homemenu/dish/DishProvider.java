package com.tovo.eat.ui.home.homemenu.dish;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DishProvider {

    @ContributesAndroidInjector(modules = DishModule.class)
    abstract DishFragment provideDishFragmentFactory();
}
