package com.tovo.eat.ui.home.homemenu.story;


import android.support.v4.app.FragmentManager;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class StoriesActivityModule {

    @Provides
    StoriesActivityViewModel provideStoriesViewModel(DataManager dataManager) {
        return new StoriesActivityViewModel(dataManager);
    }

    @Provides
    TabsPagerAdapter provideTabsAdapter(StoriesActivity activity) {
        return new TabsPagerAdapter(activity.getSupportFragmentManager());
    }
}
