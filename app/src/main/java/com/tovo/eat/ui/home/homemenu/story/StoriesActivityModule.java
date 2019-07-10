package com.tovo.eat.ui.home.homemenu.story;


import com.tovo.eat.data.DataManager;

import dagger.Module;
import dagger.Provides;

@Module
public class StoriesActivityModule {

    @Provides
    StoriesActivityViewModel provideStoriesViewModel(DataManager dataManager) {
        return new StoriesActivityViewModel(dataManager);
    }

}
