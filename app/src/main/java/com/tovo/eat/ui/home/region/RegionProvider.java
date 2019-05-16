package com.tovo.eat.ui.home.region;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class RegionProvider {

    @ContributesAndroidInjector(modules = RegionModule.class)
    abstract RegionFragment provideRegionFragmentFactory();
}
