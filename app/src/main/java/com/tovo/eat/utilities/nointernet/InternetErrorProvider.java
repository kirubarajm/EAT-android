package com.tovo.eat.utilities.nointernet;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class InternetErrorProvider {

    @ContributesAndroidInjector(modules = InternetErrorModule.class)
    abstract InternetErrorFragment provideInternetErrorFragmentFactory();

}
