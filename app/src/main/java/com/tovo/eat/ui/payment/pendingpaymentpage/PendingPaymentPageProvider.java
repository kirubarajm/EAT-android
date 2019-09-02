package com.tovo.eat.ui.payment.pendingpaymentpage;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class PendingPaymentPageProvider {

    @ContributesAndroidInjector(modules = PendingPaymentPageModule.class)
    abstract PendingPaymentPageAlert providePendingRetryActivityFactory();

}
