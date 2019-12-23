package com.tovo.eat.ui.home.homemenu.story.storiesactivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tovo.eat.ui.home.homemenu.kitchen.KitchenResponse;
import com.tovo.eat.ui.home.homemenu.story.StoriesResponse;
import com.tovo.eat.ui.home.homemenu.story.storiesactivity.fragment.StoriesPagerFragment;

public class StoriesTabAdapter extends FragmentStatePagerAdapter {

    KitchenResponse storiesFullResponse;
    private int mTabCount;

    public StoriesTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Bundle args = new Bundle();
        args.putInt("position", position);
        StoriesPagerFragment fragment = new StoriesPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return mTabCount;
    }

    public void setCount(KitchenResponse storiesFullResponse) {
        if (storiesFullResponse != null && storiesFullResponse.getResult() != null && storiesFullResponse.getResult().size() > 0&& storiesFullResponse.getResult().get(0).getStory() != null&& storiesFullResponse.getResult().get(0).getStory().size()  >0)
            this.mTabCount = storiesFullResponse.getResult().get(0).getStory().size();
        this.storiesFullResponse = storiesFullResponse;
    }


}
