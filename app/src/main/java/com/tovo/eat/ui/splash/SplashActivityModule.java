package com.tovo.eat.ui.splash;



import com.tovo.eat.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class SplashActivityModule {

    @Provides
    SplashActivityViewModel provideSplashViewModel(DataManager dataManager) {
        return new SplashActivityViewModel(dataManager);
    }

}
