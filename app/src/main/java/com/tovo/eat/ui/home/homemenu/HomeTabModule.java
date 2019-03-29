package com.tovo.eat.ui.home.homemenu;


import com.tovo.eat.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeTabModule {

    @Provides
    HomeTabViewModel provideHomeViewModel(DataManager dataManager) {
        return new HomeTabViewModel(dataManager);
    }

    @Provides
    HomeTabAdapter provideHomePagerAdapter(HomeTabFragment activityHomeTabFragment) {
        return new HomeTabAdapter(activityHomeTabFragment.getChildFragmentManager());
    }

   /* @Provides
    ViewModelProvider.Factory provideMyAccountViewModel(HomeTabViewModel mHomeTabViewModel) {
        return new ViewModelProviderFactory<>(mHomeTabViewModel);
    }*/
}
