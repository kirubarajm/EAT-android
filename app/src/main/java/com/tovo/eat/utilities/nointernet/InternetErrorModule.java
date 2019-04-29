package com.tovo.eat.utilities.nointernet;



import com.tovo.eat.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class InternetErrorModule {

    @Provides
    InternetErrorViewModel provideInternetErrorViewModel(DataManager dataManager) {
        return new InternetErrorViewModel(dataManager);
    }


}
