package com.tovo.eat.ui.home.homemenu.story.storiesactivity.ntest;

import com.tovo.eat.ui.home.homemenu.story.StoriesResponse;

public interface StoriesPagerFragmentNavigator2 {

    void handleError(Throwable throwable);

    void onSeeMore();

    void getStoriesDataFromLocal(StoriesResponse storiesResponse);
}
