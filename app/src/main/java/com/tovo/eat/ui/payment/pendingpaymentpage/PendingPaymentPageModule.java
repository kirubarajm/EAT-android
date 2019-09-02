package com.tovo.eat.ui.payment.pendingpaymentpage;

import com.tovo.eat.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class PendingPaymentPageModule {

    @Provides
    PendingPaymentPageViewModel providePaymentReryViewModel(DataManager dataManager) {
        return new PendingPaymentPageViewModel(dataManager);
    }
}
