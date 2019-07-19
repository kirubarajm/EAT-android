package com.tovo.eat.ui.home.homemenu.story.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tovo.eat.ui.account.favorites.favdish.FavDishFragment;
import com.tovo.eat.ui.account.favorites.favkitchen.FavKitchenFragment;
import com.tovo.eat.ui.home.homemenu.story.StoriesResponse;
import com.tovo.eat.ui.home.homemenu.story.library.StatusStoriesFragment;
import com.tovo.eat.ui.home.homemenu.story.sample.fragment.SamplePagerFragment;

public class SampleTabAdapter extends FragmentStatePagerAdapter {

    private int mTabCount;
    StoriesResponse storiesFullResponse;

    public SampleTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Bundle args = new Bundle();
        args.putInt("position",position);
        SamplePagerFragment fragment = new SamplePagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return mTabCount;
    }

    public void setCount(StoriesResponse storiesFullResponse){
        this.mTabCount = storiesFullResponse.getResult().size();
        this.storiesFullResponse = storiesFullResponse;
    }

}
