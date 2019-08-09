package com.tovo.eat.ui.home.homemenu.story.storiesactivity.fragment;



import com.tovo.eat.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class StoriesPagerFragmentModule {

    @Provides
    StoriesPagerFragmentViewModel providesampleViewModel(DataManager dataManager) {
        return new StoriesPagerFragmentViewModel(dataManager);
    }

}
