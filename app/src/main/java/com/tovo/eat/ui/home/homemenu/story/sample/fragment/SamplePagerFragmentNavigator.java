package com.tovo.eat.ui.home.homemenu.story.sample.fragment;

import com.tovo.eat.ui.home.homemenu.story.StoriesResponse;

public interface SamplePagerFragmentNavigator {

    void handleError(Throwable throwable);

    void getStoriesDataFromLocal(StoriesResponse storiesResponse);
}
