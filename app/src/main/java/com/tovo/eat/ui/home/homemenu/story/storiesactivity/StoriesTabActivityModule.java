package com.tovo.eat.ui.home.homemenu.story.storiesactivity;

import com.tovo.eat.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class StoriesTabActivityModule {

    @Provides
    StoriesTabActivityViewModel provideSampleTabViewModel(DataManager dataManager) {
        return new StoriesTabActivityViewModel(dataManager);
    }

  /*  @Provides
    StoriesTabAdapter provideFavoritesTabPagerAdapter(StoriesTabActivity mFavoritesTabActivity) {
        return new StoriesTabAdapter(mFavoritesTabActivity.getChildFragmentManager());
    }*/

    @Provides
    StoriesTabAdapter provideSampleTabPagerAdapter(StoriesTabActivity mFavoritesTabActivity) {
        return new StoriesTabAdapter(mFavoritesTabActivity.getSupportFragmentManager());
    }
}
