package com.tovo.eat.ui.pendingpayment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class PendingPaymentProvider {

    @ContributesAndroidInjector(modules = PendingPaymentModule.class)
    abstract PendingPaymentAlert provideOrderRatingActivityFactory();

}
