package com.tovo.eat.ui.signup.namegender;

import com.tovo.eat.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class NameGenderActivityModule {

    @Provides
    NameGenderActivityViewModel provideNameGenderViewModel(DataManager dataManager) {
        return new NameGenderActivityViewModel(dataManager);
    }
}
