package com.tovo.eat.ui.orderrating;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class OrderRatingProvider {

    @ContributesAndroidInjector(modules = OrderRatingActivityModule.class)
    abstract OrderRatingActivity provideOrderRatingActivityFactory();

}
