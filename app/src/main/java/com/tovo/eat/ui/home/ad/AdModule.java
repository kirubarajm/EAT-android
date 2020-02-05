package com.tovo.eat.ui.home.ad;



import com.tovo.eat.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class AdModule {

    @Provides
    AdViewModel provideTandCViewModel(DataManager dataManager) {
        return new AdViewModel(dataManager);
    }
}
