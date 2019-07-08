package com.tovo.eat.ui.kitchendetails.dialog;


import com.tovo.eat.ui.home.homemenu.dish.dialog.DialogChangeKitchenModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DialogChangeKitchenDetailsProvider {

    @ContributesAndroidInjector(modules = DialogChangeKitchenModule.class)
    abstract DialogChangeKitchen provideChangeKitchenDishFactory();

}
