package com.tovo.eat.ui.track;


import com.tovo.eat.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class OrderTrackingModule {

    @Provides
    OrderTrackingViewModel provideDirectionViewModel(DataManager dataManager)
    {
        return new OrderTrackingViewModel(dataManager);
    }
}
