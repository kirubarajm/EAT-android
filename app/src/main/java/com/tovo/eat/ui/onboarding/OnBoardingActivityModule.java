package com.tovo.eat.ui.onboarding;

import com.tovo.eat.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class OnBoardingActivityModule {

    @Provides
    OnBoardingActivityViewModel provideSplashViewModel(DataManager dataManager) {
        return new OnBoardingActivityViewModel(dataManager);
    }

}
