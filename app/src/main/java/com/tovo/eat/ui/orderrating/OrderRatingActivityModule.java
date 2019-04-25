package com.tovo.eat.ui.orderrating;

import com.tovo.eat.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class OrderRatingActivityModule {

    @Provides
    OrderRatingActivityViewModel provideOrderRatingViewModel(DataManager dataManager) {
        return new OrderRatingActivityViewModel(dataManager);
    }
}
