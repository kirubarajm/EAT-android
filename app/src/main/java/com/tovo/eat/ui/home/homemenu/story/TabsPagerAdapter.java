package com.tovo.eat.ui.home.homemenu.story;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.tovo.eat.ui.home.homemenu.story.library.StatusStoriesFragment;


public class TabsPagerAdapter extends FragmentPagerAdapter {

    StoriesResponse storiesFullResponse;
    int Count;

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
        //this.storiesFullResponse = storiesFullResponse;
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return Count;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {

        Bundle args = new Bundle();
        //args.putSerializable("fullResponse", (Serializable) storiesFullResponse.getResult());
        args.putInt("position",position);
        StatusStoriesFragment fragment = new StatusStoriesFragment();
        fragment.setArguments(args);
        //StatusStoriesFragment fragment = new StatusStoriesFragment();
        return fragment;
    }

    public void setCount(int Count,StoriesResponse storiesFullResponse){
        this.Count = Count;
        this.storiesFullResponse = storiesFullResponse;
    }
}
