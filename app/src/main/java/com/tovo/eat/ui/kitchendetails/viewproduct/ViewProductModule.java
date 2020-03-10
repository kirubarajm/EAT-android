package com.tovo.eat.ui.kitchendetails.viewproduct;

import android.support.v7.widget.LinearLayoutManager;

import com.tovo.eat.data.DataManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewProductModule {

    DataManager dataManager;

    @Provides
    ViewProductViewModel provideKitchenDishViewModel(DataManager dataManager) {
        this.dataManager = dataManager;

        return new ViewProductViewModel(dataManager);
    }

    @Provides
    ViewProductsImageAdapter provideViewProductsImageAdapter() {
        return new ViewProductsImageAdapter(new ArrayList<>(),dataManager);
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(ViewProductActivity activity) {
        return new LinearLayoutManager(activity);
    }
}
