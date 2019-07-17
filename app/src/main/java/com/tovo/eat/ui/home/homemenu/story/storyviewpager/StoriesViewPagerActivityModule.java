package com.tovo.eat.ui.home.homemenu.story.storyviewpager;



import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.home.homemenu.story.TabsPagerAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class StoriesViewPagerActivityModule {

    @Provides
    StoriesViewPagerActivityViewModel provideStoriesViewModel(DataManager dataManager) {
        return new StoriesViewPagerActivityViewModel(dataManager);
    }

    @Provides
    TabsPagerAdapter provideStoriesTabPagerAdapter(StoriesViewPagerActivity activityMyAccountTabFragment) {
        return new TabsPagerAdapter(activityMyAccountTabFragment.getSupportFragmentManager());
    }
}
