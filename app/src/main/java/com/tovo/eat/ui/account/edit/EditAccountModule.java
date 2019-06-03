package com.tovo.eat.ui.account.edit;

import com.tovo.eat.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class EditAccountModule {

    @Provides
    EditAccountViewModel provideNameGenderViewModel(DataManager dataManager) {
        return new EditAccountViewModel(dataManager);
    }
}
