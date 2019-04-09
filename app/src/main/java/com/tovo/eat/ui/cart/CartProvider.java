package com.tovo.eat.ui.cart;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class CartProvider {

    @ContributesAndroidInjector(modules = CartModule.class)
    abstract CartActivity provideCartActivityFactory();





}
