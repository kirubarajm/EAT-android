package com.tovo.eat.ui.account.favorites;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityTabFavoritesBinding;
import com.tovo.eat.ui.base.BaseActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;


public class FavoritesTabActivity extends BaseActivity<ActivityTabFavoritesBinding, FavoritesTabActivityViewModel> implements FavoritesTabActivityNavigator, HasSupportFragmentInjector {

    ActivityTabFavoritesBinding mActivityTabFavoritesBinding;
    @Inject
    FavoritesTabActivityViewModel mFavoritesActivityViewModel;
    @Inject
    FavoritesTabAdapter mFavoritesTabAdapter;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    public static Intent newIntent(Context context) {

        return new Intent(context, FavoritesTabActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public int getBindingVariable() {
        return BR.favTabViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_tab_favorites;
    }

    @Override
    public FavoritesTabActivityViewModel getViewModel() {
        return mFavoritesActivityViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityTabFavoritesBinding = getViewDataBinding();
        mFavoritesActivityViewModel.setNavigator(this);
        setUp();
    }

    private void setUp() {
        mActivityTabFavoritesBinding.tabFavorites.setSelectedTabIndicatorColor(Color.parseColor("#0F5E9E"));
        mFavoritesTabAdapter.setCount(2);
        mActivityTabFavoritesBinding.viewPagerFavorites.setAdapter(mFavoritesTabAdapter);
        mActivityTabFavoritesBinding.tabFavorites.addTab(mActivityTabFavoritesBinding.tabFavorites.newTab().setText(getString(R.string.kitchen)));
        mActivityTabFavoritesBinding.tabFavorites.addTab(mActivityTabFavoritesBinding.tabFavorites.newTab().setText(getString(R.string.dish)));
        mActivityTabFavoritesBinding.viewPagerFavorites.setOffscreenPageLimit(mActivityTabFavoritesBinding.tabFavorites.getTabCount());
        mActivityTabFavoritesBinding.viewPagerFavorites.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mActivityTabFavoritesBinding.tabFavorites));
        mActivityTabFavoritesBinding.tabFavorites.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mActivityTabFavoritesBinding.viewPagerFavorites.setCurrentItem(tab.getPosition());
                //mActivityTabFavoritesBinding.tabFavorites.setSelectedTabIndicatorColor(Color.parseColor("#0F5E9E"));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
