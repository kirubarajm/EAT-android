package com.tovo.eat.ui.home.homemenu;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class HomeTabProvider {

    @ContributesAndroidInjector(modules = HomeTabModule.class)
    abstract HomeTabFragment provideHomeTabFragmentFactory();
}
