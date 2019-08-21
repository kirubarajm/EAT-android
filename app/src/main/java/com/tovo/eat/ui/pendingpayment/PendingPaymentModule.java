package com.tovo.eat.ui.pendingpayment;

import com.tovo.eat.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class PendingPaymentModule {

    @Provides
    PendingPaymentViewModel provideOrderRatingViewModel(DataManager dataManager) {
        return new PendingPaymentViewModel(dataManager);
    }
}
