package com.tovo.eat.ui.account.feedbackandsupport.feedback;

import com.tovo.eat.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class FeedbackActivityModule {


    @Provides
    FeedbackActivityViewModel provideFeedbackAndSupportViewModel(DataManager dataManager) {
        return new FeedbackActivityViewModel(dataManager);
    }

}
