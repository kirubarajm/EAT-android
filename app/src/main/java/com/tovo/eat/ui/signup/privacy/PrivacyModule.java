package com.tovo.eat.ui.signup.privacy;



import com.tovo.eat.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class PrivacyModule {

    @Provides
    PrivacyViewModel provideTandCViewModel(DataManager dataManager) {
        return new PrivacyViewModel(dataManager);
    }
}
