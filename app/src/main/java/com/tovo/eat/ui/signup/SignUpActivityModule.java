package com.tovo.eat.ui.signup;

import com.tovo.eat.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class SignUpActivityModule {

    @Provides
    SignUpActivityViewModel provideSignUpViewModel(DataManager dataManager) {
        return new SignUpActivityViewModel(dataManager);
    }
}
