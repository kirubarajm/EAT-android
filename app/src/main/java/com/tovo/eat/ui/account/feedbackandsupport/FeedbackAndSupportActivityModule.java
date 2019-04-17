package com.tovo.eat.ui.account.feedbackandsupport;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.account.MyAccountViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class FeedbackAndSupportActivityModule {

    @Provides
    FeedbackAndSupportActivityViewModel provideFeedbackAndSupportViewModel(DataManager dataManager) {
        return new FeedbackAndSupportActivityViewModel(dataManager);
    }

}
