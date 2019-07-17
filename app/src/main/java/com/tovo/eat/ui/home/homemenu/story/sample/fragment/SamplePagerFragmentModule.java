package com.tovo.eat.ui.home.homemenu.story.sample.fragment;



import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.home.homemenu.story.TabsPagerAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class SamplePagerFragmentModule {

    @Provides
    SamplePagerFragmentViewModel providesampleViewModel(DataManager dataManager) {
        return new SamplePagerFragmentViewModel(dataManager);
    }

}
