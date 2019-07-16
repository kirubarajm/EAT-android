package com.tovo.eat.ui.home.homemenu.story.library;


import com.tovo.eat.data.DataManager;
import dagger.Module;
import dagger.Provides;

@Module
public class StatusStoriesModule {

    @Provides
    StatusStoriesFramentViewModel provideStatusStoriesViewModel(DataManager dataManager) {
        return new StatusStoriesFramentViewModel(dataManager);
    }

}
