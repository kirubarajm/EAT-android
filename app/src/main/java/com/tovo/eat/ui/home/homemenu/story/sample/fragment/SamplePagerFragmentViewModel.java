package com.tovo.eat.ui.home.homemenu.story.sample.fragment;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.ui.home.homemenu.story.StoriesResponse;

public class SamplePagerFragmentViewModel extends BaseViewModel<SamplePagerFragmentNavigator> {

    StoriesResponse storiesResponse;

    public SamplePagerFragmentViewModel(DataManager dataManager) {
        super(dataManager);
        Gson sGson = new GsonBuilder().create();
        storiesResponse = sGson.fromJson(getDataManager().getStoriesList(), StoriesResponse.class);
    }

    public void getData(){
        getNavigator().getStoriesDataFromLocal(storiesResponse);
    }

}
