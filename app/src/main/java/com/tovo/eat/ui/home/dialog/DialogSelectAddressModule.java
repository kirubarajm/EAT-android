package com.tovo.eat.ui.home.dialog;



import com.tovo.eat.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class DialogSelectAddressModule {

    @Provides
    DialogSelectAddressViewModel provideSelectAddressViewModel(DataManager dataManager) {
        return new DialogSelectAddressViewModel(dataManager);
    }

}
