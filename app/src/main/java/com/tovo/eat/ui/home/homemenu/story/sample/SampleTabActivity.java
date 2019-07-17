package com.tovo.eat.ui.home.homemenu.story.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivitySampleBinding;
import com.tovo.eat.ui.account.favorites.favdish.CartFavListener;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.MainActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;


public class SampleTabActivity extends BaseActivity<ActivitySampleBinding, SampleTabActivityViewModel> implements SampleTabActivityNavigator,
        HasSupportFragmentInjector {

    ActivitySampleBinding mActivitySampleBinding;
    @Inject
    SampleTabActivityViewModel mFavoritesActivityViewModel;
    @Inject
    SampleTabAdapter mFavoritesTabAdapter;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    public static Intent newIntent(Context context) {

        return new Intent(context, SampleTabActivity.class);
    }


    @Override
    public void handleError(Throwable throwable) {

    }


    @Override
    public int getBindingVariable() {
        return BR.sampleViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_sample;
    }

    @Override
    public SampleTabActivityViewModel getViewModel() {
        return mFavoritesActivityViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFavoritesActivityViewModel.setNavigator(this);
        mActivitySampleBinding = getViewDataBinding();
        setUp();
    }

    private void setUp() {
        mFavoritesTabAdapter.setCount(2);
        mActivitySampleBinding.viewPagerSample.setAdapter(mFavoritesTabAdapter);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
