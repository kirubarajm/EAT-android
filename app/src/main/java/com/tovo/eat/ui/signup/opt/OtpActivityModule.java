package com.tovo.eat.ui.signup.opt;

import com.tovo.eat.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class OtpActivityModule {

    @Provides
    OtpActivityViewModel provideSignUpViewModel(DataManager dataManager) {
        return new OtpActivityViewModel(dataManager);
    }
}
