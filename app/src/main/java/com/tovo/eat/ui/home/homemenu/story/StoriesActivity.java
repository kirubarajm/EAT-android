package com.tovo.eat.ui.home.homemenu.story;


import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import com.android.databinding.library.baseAdapters.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityStoriesBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.homemenu.story.library.CubeTransformer;

import javax.inject.Inject;

public class StoriesActivity extends BaseActivity<ActivityStoriesBinding, StoriesActivityViewModel>
        implements StoriesActivityNavigator {

    private ViewPager viewPager;

    @Inject
    TabsPagerAdapter myAdapter;
    StoriesResponse storiesFullResponse;
    int position;

    @Inject
    StoriesActivityViewModel mStoriesActivityViewModel;

    private ActivityStoriesBinding mActivityStoriesBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, StoriesActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public int getBindingVariable() {
        return BR.storiesViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_stories;
    }

    @Override
    public StoriesActivityViewModel getViewModel() {
        return mStoriesActivityViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityStoriesBinding = getViewDataBinding();
        mStoriesActivityViewModel.setNavigator(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            storiesFullResponse= (StoriesResponse) bundle.getSerializable("fullStories");
            position=bundle.getInt("position");
        }

        viewPager = findViewById(R.id.pager);
        myAdapter.setCount(5);
        //myAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        viewPager.setPageTransformer(true, new CubeTransformer());
        viewPager.setAdapter(myAdapter);

        ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        };
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

}
