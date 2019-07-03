package com.tovo.eat.ui.signup.tandc;



import com.tovo.eat.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class TermsAndConditionModule {

    @Provides
    TermsAndConditionViewModel provideTandCViewModel(DataManager dataManager) {
        return new TermsAndConditionViewModel(dataManager);
    }
}
