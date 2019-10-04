package com.tovo.eat.ui.cart.funnel;

import com.tovo.eat.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class FunnelModule {


    @Provides
    FunnelViewModel provideOrderPlacedViewModel(DataManager dataManager) {
        return new FunnelViewModel(dataManager);
    }

}
