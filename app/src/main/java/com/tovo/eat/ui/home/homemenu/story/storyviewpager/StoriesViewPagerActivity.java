package com.tovo.eat.ui.home.homemenu.story.storyviewpager;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.android.databinding.library.baseAdapters.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityStoriesBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.homemenu.story.StoriesResponse;
import com.tovo.eat.ui.home.homemenu.story.TabsPagerAdapter;
import com.tovo.eat.ui.home.homemenu.story.library.CubeTransformer;
import com.tovo.eat.ui.home.homemenu.story.library.StatusStoriesFragment;
import com.tovo.eat.ui.onboarding.OnBoardingActivity;
import com.tovo.eat.ui.splash.SplashActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class StoriesViewPagerActivity extends BaseActivity<ActivityStoriesBinding, StoriesViewPagerActivityViewModel>
        implements StoriesViewPagerActivityNavigator,HasSupportFragmentInjector {

    @Inject
    StoriesViewPagerActivityViewModel mSplashActivityViewModel;
    private ActivityStoriesBinding mActivityStoriesBinding;
    StoriesResponse storiesFullResponse;
    int position;
    @Inject
    TabsPagerAdapter mTabsPagerAdapter;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;


    public static Intent newIntent(Context context) {

        return new Intent(context, StoriesViewPagerActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public int getBindingVariable() {
        return BR.storiesViewPagerViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_stories;
    }

    @Override
    public StoriesViewPagerActivityViewModel getViewModel() {
        return mSplashActivityViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSplashActivityViewModel.setNavigator(this);
        mActivityStoriesBinding = getViewDataBinding();

        Bundle bundle = getIntent().getExtras();

        if (bundle!=null){
            storiesFullResponse = (StoriesResponse) bundle.getSerializable("fullStories");
            position = bundle.getInt("position");
        }

        mTabsPagerAdapter.setCount(storiesFullResponse.getResult().size(),storiesFullResponse);
        mActivityStoriesBinding.pager.setPageTransformer(true, new CubeTransformer());
        mActivityStoriesBinding.pager.setAdapter(mTabsPagerAdapter);


        ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                methodDDD();
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        };
        mActivityStoriesBinding.pager.addOnPageChangeListener(viewPagerPageChangeListener);

        mActivityStoriesBinding.pager.setCurrentItem(mActivityStoriesBinding.pager.getCurrentItem() + position);

        //Fragment activeFragment = mTabsPagerAdapter.getItem(mActivityStoriesBinding.pager.getCurrentItem());
        //((StatusStoriesFragment)activeFragment).setUserVisibleHint(mActivityStoriesBinding.pager.getCurrentItem(),storiesFullResponse);
        //methodDDD();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                methodDDD();
            }
        },500);

    }

    private void methodDDD(){
        StatusStoriesFragment frag1 = (StatusStoriesFragment)mActivityStoriesBinding.pager
                .getAdapter()
                .instantiateItem(mActivityStoriesBinding.pager, mActivityStoriesBinding.pager.getCurrentItem());
        frag1.setUserVisibleHint(mActivityStoriesBinding.pager.getCurrentItem(),storiesFullResponse);
    }
    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
