package com.tovo.eat.ui.alerts.ordercanceled;

import com.tovo.eat.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class OrderCanceledModule {

    @Provides
    OrderCanceledViewModel provideOrderRatingViewModel(DataManager dataManager) {
        return new OrderCanceledViewModel(dataManager);
    }
}
