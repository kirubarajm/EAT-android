package com.tovo.eat.ui.home.homemenu;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenAdapter;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenFragment;
import com.tovo.eat.ui.home.homemenu.story.StoriesCardAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeTabModule {

    Context context;

    @Provides
    HomeTabViewModel provideHomeViewModel(DataManager dataManager) {
        return new HomeTabViewModel(dataManager);
    }

    @Provides
    KitchenAdapter provideKitchenAdapter() {
        return new KitchenAdapter(new ArrayList<>());
    }


    @Provides
    RegionsCardAdapter provideRegionnAdapter() {
        return new RegionsCardAdapter(new ArrayList<>());
    }

    @Provides
    StoriesCardAdapter provideStoriesAdapter() {
        return new StoriesCardAdapter(new ArrayList<>());
    }


    @Provides
    LinearLayoutManager provideLinearLayoutManager(KitchenFragment fragment) {
        this.context = fragment.getActivity();
        return new LinearLayoutManager(fragment.getActivity());
    }
   /* @Provides
    HomeTabAdapter provideHomePagerAdapter(HomeTabFragment activityHomeTabFragment) {
        return new HomeTabAdapter(activityHomeTabFragment.getChildFragmentManager());
    }*/

   /* @Provides
    ViewModelProvider.Factory provideMyAccountViewModel(HomeTabViewModel mHomeTabViewModel) {
        return new ViewModelProviderFactory<>(mHomeTabViewModel);
    }*/
}
