package com.tovo.eat.ui.home.homemenu.story.storiesactivity.fragment;


import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.ui.home.homemenu.story.StoriesResponse;

public class StoriesPagerFragmentViewModel extends BaseViewModel<StoriesPagerFragmentNavigator> {

    StoriesResponse storiesResponse;

    public ObservableField<String> title = new ObservableField<>();
    public ObservableField<String> subTitle = new ObservableField<>();
    public ObservableField<Integer> category_type = new ObservableField<>();
    public ObservableField<Long> category_id = new ObservableField<>();
    public ObservableField<String> imageUrl = new ObservableField<>();
    public ObservableBoolean aBooleanImg = new ObservableBoolean();

    public StoriesPagerFragmentViewModel(DataManager dataManager) {
        super(dataManager);
        Gson sGson = new GsonBuilder().create();
        storiesResponse = sGson.fromJson(getDataManager().getStoriesList(), StoriesResponse.class);
    }

    public void getData(){
        getNavigator().getStoriesDataFromLocal(storiesResponse);
    }

    public void onClickSeeMore(){
        getNavigator().onSeeMore();
    }

}
