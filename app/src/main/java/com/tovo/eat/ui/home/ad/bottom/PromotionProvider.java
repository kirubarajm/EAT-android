package com.tovo.eat.ui.home.ad.bottom;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class PromotionProvider {

    @ContributesAndroidInjector(modules = PromotionModule.class)
    abstract PromotionFragment providePromotionFragmentFactory();

}
