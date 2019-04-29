package com.tovo.eat.ui.home.dialog;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DialogSelectAddressProvider {

    @ContributesAndroidInjector(modules = DialogSelectAddressModule.class)
    abstract DialogSelectAddress provideSelectAddressFactory();

}
