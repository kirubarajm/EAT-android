package com.tovo.eat.ui.cart.xfactoralert;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class XFactorProvider {

    @ContributesAndroidInjector(modules = XFactorModule.class)
    abstract XFactorFragment providexFactorFragmentFactory();

}
