package com.tovo.eat.ui.registration;

import com.tovo.eat.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class RegistrationActivityModule {

    @Provides
    RegistrationActivityViewModel provideSignUpViewModel(DataManager dataManager) {
        return new RegistrationActivityViewModel(dataManager);
    }
}
