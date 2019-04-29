package com.tovo.eat.ui.home.homemenu.dish.dialog;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DialogChangeKitchenProvider {

    @ContributesAndroidInjector(modules = DialogChangeKitchenModule.class)
    abstract DialogChangeKitchen provideChangeKitchenFactory();

}
