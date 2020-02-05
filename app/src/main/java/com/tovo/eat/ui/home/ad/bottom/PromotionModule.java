package com.tovo.eat.ui.home.ad.bottom;



import android.support.v7.widget.LinearLayoutManager;

import com.tovo.eat.data.DataManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class PromotionModule {


    DataManager dataManager;
    @Provides
    PromotionViewModel provideFilterViewModel(DataManager dataManager) {
        this.dataManager=dataManager;
        return new PromotionViewModel(dataManager);
    }



}
