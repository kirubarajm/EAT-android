package com.tovo.eat.ui.home.homemenu.story.library;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.ui.home.homemenu.story.StoriesResponse;

public class StatusStoriesFramentViewModel extends BaseViewModel<StatusStoriesNavigator> {

    StoriesResponse storiesResponse;
    public StatusStoriesFramentViewModel(DataManager dataManager) {
        super(dataManager);

        Gson sGson = new GsonBuilder().create();
        storiesResponse = sGson.fromJson(getDataManager().getStoriesList(), StoriesResponse.class);
        //getStoriesDataFromLocal(storiesResponse);
    }

    public void getData(){
        getNavigator().getStoriesDataFromLocal(storiesResponse);
    }
}
