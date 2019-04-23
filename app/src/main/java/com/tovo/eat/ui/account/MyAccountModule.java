package com.tovo.eat.ui.account;



import com.tovo.eat.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class MyAccountModule {

    @Provides
    MyAccountViewModel provideTrainingViewModel(DataManager dataManager) {
        return new MyAccountViewModel(dataManager);
    }


   /* @Provides
    ViewModelProvider.Factory provideTrainingViewModel(FilterViewModel blogViewModel) {
        return new ViewModelProviderFactory<>(blogViewModel);
    }*/

}
