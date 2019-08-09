package com.tovo.eat.ui.home.homemenu.story.storiesactivity.fragment;

import com.tovo.eat.ui.home.homemenu.story.StoriesResponse;

public interface StoriesPagerFragmentNavigator {

    void handleError(Throwable throwable);

    void onSeeMore();

    void getStoriesDataFromLocal(StoriesResponse storiesResponse);
}
