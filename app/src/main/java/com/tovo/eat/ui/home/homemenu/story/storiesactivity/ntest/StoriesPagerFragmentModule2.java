package com.tovo.eat.ui.home.homemenu.story.storiesactivity.ntest;



import com.tovo.eat.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class StoriesPagerFragmentModule2 {

    @Provides
    StoriesPagerFragmentViewModel2 providesampleViewModel(DataManager dataManager) {
        return new StoriesPagerFragmentViewModel2(dataManager);
    }

}
