package com.tovo.eat.ui.account.feedbackandsupport.support;


import com.tovo.eat.data.DataManager;
import dagger.Module;
import dagger.Provides;

@Module
public class SupportActivityModule {

    @Provides
    SupportActivityViewModel provideQueriesViewModel(DataManager dataManager)
    {
        return new SupportActivityViewModel(dataManager);
    }

}
