package com.tovo.eat.ui.account.favorites;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tovo.eat.ui.account.favorites.favdish.FavoritesDishFragment;
import com.tovo.eat.ui.account.favorites.favkitchen.FavoritesKitchenFragment;

public class FavoritesTabAdapter extends FragmentStatePagerAdapter {

    private int mTabCount;

    public FavoritesTabAdapter(FragmentManager fm) {
        super(fm);
        this.mTabCount = 0;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return FavoritesDishFragment.newInstance();
            case 1:
                return FavoritesKitchenFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mTabCount;
    }

    public void setCount(int count) {
        mTabCount = count;
    }
}
