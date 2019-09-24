package com.tovo.eat.ui.home.homemenu.dish.dialog;


import com.tovo.eat.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class DialogChangeKitchenModule {

    @Provides
    DialogChangeKitchenViewModel provideChangeKitchenViewModel(DataManager dataManager) {
        return new DialogChangeKitchenViewModel(dataManager);
    }

}
