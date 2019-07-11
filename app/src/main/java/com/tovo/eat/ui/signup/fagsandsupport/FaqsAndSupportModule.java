package com.tovo.eat.ui.signup.fagsandsupport;

import com.tovo.eat.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class FaqsAndSupportModule {

    @Provides
    FaqsAndSupportViewModel provideFeedbackAndSupportViewModel(DataManager dataManager) {
        return new FaqsAndSupportViewModel(dataManager);
    }

}
