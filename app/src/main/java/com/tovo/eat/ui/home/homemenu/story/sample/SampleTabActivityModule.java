package com.tovo.eat.ui.home.homemenu.story.sample;

import com.tovo.eat.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class SampleTabActivityModule {

    @Provides
    SampleTabActivityViewModel provideSampleTabViewModel(DataManager dataManager) {
        return new SampleTabActivityViewModel(dataManager);
    }

  /*  @Provides
    SampleTabAdapter provideFavoritesTabPagerAdapter(SampleTabActivity mFavoritesTabActivity) {
        return new SampleTabAdapter(mFavoritesTabActivity.getChildFragmentManager());
    }*/

    @Provides
    SampleTabAdapter provideSampleTabPagerAdapter(SampleTabActivity mFavoritesTabActivity) {
        return new SampleTabAdapter(mFavoritesTabActivity.getSupportFragmentManager());
    }
}
