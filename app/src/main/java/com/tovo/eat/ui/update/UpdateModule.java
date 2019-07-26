package com.tovo.eat.ui.update;



import com.tovo.eat.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class UpdateModule {

    @Provides
    UpdateViewModel provideSplashViewModel(DataManager dataManager) {
        return new UpdateViewModel(dataManager);
    }

}
