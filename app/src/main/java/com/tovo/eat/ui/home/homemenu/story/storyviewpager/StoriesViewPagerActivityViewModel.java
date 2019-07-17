package com.tovo.eat.ui.home.homemenu.story.storyviewpager;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.ui.home.homemenu.story.StoriesResponse;

public class StoriesViewPagerActivityViewModel extends BaseViewModel<StoriesViewPagerActivityNavigator> {

    StoriesResponse storiesResponse;

    public StoriesViewPagerActivityViewModel(DataManager dataManager) {
        super(dataManager);
    }

}
