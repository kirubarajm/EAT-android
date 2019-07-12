package com.tovo.eat.ui.kitchendetails.viewimage;

import android.support.v7.widget.LinearLayoutManager;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.kitchendetails.FavoriteAdapter;
import com.tovo.eat.ui.kitchendetails.FoodBadgeAdapter;
import com.tovo.eat.ui.kitchendetails.KitchenDetailsActivity;
import com.tovo.eat.ui.kitchendetails.KitchenDetailsViewModel;
import com.tovo.eat.ui.kitchendetails.MenuKitchenInfoCommonImageAdapter;
import com.tovo.eat.ui.kitchendetails.SpecialitiesAdapter;
import com.tovo.eat.ui.kitchendetails.TodaysMenuAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewImageModule {

    @Provides
    ViewImageViewModel provideViewImageViewModel(DataManager dataManager) {
        return new ViewImageViewModel(dataManager);
    }


}
