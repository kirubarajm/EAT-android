package com.tovo.eat.ui.registration;

import android.app.Activity;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.account.orderhistory.historylist.OrdersHistoryActivityAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class RegistrationActivityModule {

    @Provides
    RegistrationActivityViewModel provideSignUpViewModel(DataManager dataManager) {
        return new RegistrationActivityViewModel(dataManager);
    }

   /* @Provides
    RegionAdapter provideRegionListAdapter() {
        return new RegionAdapter(new ArrayList());
    }*/
}
