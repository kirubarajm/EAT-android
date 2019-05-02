package com.tovo.eat.ui.orderplaced;

import com.tovo.eat.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class OrderPlacedModule {


    @Provides
    OrderPlacedViewModel provideOrderPlacedViewModel(DataManager dataManager) {
        return new OrderPlacedViewModel(dataManager);
    }

}
