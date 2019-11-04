package com.tovo.eat.ui.cart.xfactoralert;



import com.tovo.eat.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class XFactorModule {

    @Provides
    XFactorViewModel provideFilterViewModel(DataManager dataManager) {
        return new XFactorViewModel(dataManager);
    }



}
