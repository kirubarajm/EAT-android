package com.tovo.eat.ui.account.favorites.dialog;


import com.tovo.eat.ui.home.homemenu.dish.dialog.DialogChangeKitchenModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DialogChangeKitchenDishProvider {

    @ContributesAndroidInjector(modules = DialogChangeKitchenModule.class)
    abstract DialogChangeKitchen provideChangeKitchenDishFactory();

}
