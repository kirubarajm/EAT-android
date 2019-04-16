package com.tovo.eat.ui.account.referrals;

import com.tovo.eat.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class ReferralsActivityModule {


    @Provides
    ReferralsActivityViewModel provideReferralsViewModel(DataManager dataManager) {
        return new ReferralsActivityViewModel(dataManager);
    }

}
