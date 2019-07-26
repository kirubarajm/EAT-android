package com.tovo.eat.ui.alerts.ordercanceled;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class OrderCanceledProvider {

    @ContributesAndroidInjector(modules = OrderCanceledModule.class)
    abstract OrderCanceledBottomFragment provideOrderRatingActivityFactory();

}
