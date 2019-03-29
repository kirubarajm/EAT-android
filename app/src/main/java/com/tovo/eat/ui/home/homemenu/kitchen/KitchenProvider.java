package com.tovo.eat.ui.home.homemenu.kitchen;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class KitchenProvider {

    @ContributesAndroidInjector(modules = KitchenModule.class)
    abstract KitchenFragment provideKitchenFragmentFactory();
}
