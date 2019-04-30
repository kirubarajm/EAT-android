package com.tovo.eat.ui.forgotpassword;



import com.tovo.eat.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class ForgotPasswordActivityModule {

    @Provides
    ForgotPasswordActivityViewModel provideSplashViewModel(DataManager dataManager) {
        return new ForgotPasswordActivityViewModel(dataManager);
    }

}
