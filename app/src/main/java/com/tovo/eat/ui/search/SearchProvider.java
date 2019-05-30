package com.tovo.eat.ui.search;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class SearchProvider {

    @ContributesAndroidInjector(modules = SearchModule.class)
    abstract SearchFragment provideSearchFragmentFactory();
}
