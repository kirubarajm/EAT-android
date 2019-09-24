package com.tovo.eat.ui.cart.refund.alert;


import com.tovo.eat.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class DialogRefundAlertModule {

    @Provides
    DialogRefundAlertViewModel provideRefundAlertViewModel(DataManager dataManager) {
        return new DialogRefundAlertViewModel(dataManager);
    }

}
