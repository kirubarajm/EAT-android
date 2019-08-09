package com.tovo.eat.ui.home.homemenu.story.storiesactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivitySampleBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.homemenu.story.StoriesResponse;
import com.tovo.eat.ui.home.homemenu.story.library.CubeTransformer;
import com.tovo.eat.ui.home.homemenu.story.storiesactivity.fragment.StoriesPagerFragment;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;


public class StoriesTabActivity extends BaseActivity<ActivitySampleBinding, StoriesTabActivityViewModel> implements StoriesTabActivityNavigator,
        HasSupportFragmentInjector {

    ActivitySampleBinding mActivitySampleBinding;
    @Inject
    StoriesTabActivityViewModel mFavoritesActivityViewModel;
    @Inject
    StoriesTabAdapter mFavoritesTabAdapter;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    int position=0,crPosition=0;
    StoriesResponse storiesFullResponse;

    public static Intent newIntent(Context context) {

        return new Intent(context, StoriesTabActivity.class);
    }

    public void methodDDD(int pos){
        if (pos==position) {
        StoriesPagerFragment frag1 = (StoriesPagerFragment)mActivitySampleBinding.viewPagerSample
                .getAdapter()
                .instantiateItem(mActivitySampleBinding.viewPagerSample, mActivitySampleBinding.viewPagerSample.getCurrentItem());
        frag1.onPlayStory(position);
        }
    }

    public void moveToNext(){

        mActivitySampleBinding.viewPagerSample.setCurrentItem(mActivitySampleBinding.viewPagerSample.getCurrentItem()+1);
    }

    public void moveToPrevious(){

        mActivitySampleBinding.viewPagerSample.setCurrentItem(mActivitySampleBinding.viewPagerSample.getCurrentItem()-1);
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
    public StoriesTabActivityViewModel getViewModel() {
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
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            storiesFullResponse = (StoriesResponse) bundle.getSerializable("fullStories");
            position = bundle.getInt("position");
        }

        mFavoritesTabAdapter.setCount(storiesFullResponse);
        mActivitySampleBinding.viewPagerSample.setPageTransformer(true, new CubeTransformer());
        mActivitySampleBinding.viewPagerSample.setAdapter(mFavoritesTabAdapter);
        mActivitySampleBinding.viewPagerSample.setOffscreenPageLimit(1);

        ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int positions) {
                position = positions;
                StoriesPagerFragment frag1 = (StoriesPagerFragment)mActivitySampleBinding.viewPagerSample
                        .getAdapter()
                        .instantiateItem(mActivitySampleBinding.viewPagerSample, mActivitySampleBinding.viewPagerSample.getCurrentItem());
                frag1.onPlayStory(positions);

                StoriesPagerFragment fragmentToPrev = (StoriesPagerFragment)mActivitySampleBinding.viewPagerSample.getAdapter().instantiateItem(mActivitySampleBinding.viewPagerSample, crPosition);
                fragmentToPrev.onPasue(positions);
                crPosition = positions;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

                Log.e("",""+arg0);
                Log.e("",""+arg1);
                Log.e("",""+arg2);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                 Log.e("",""+state+"---ViewPager.SCROLL_STATE_DRAGGING---"+ViewPager.SCROLL_STATE_DRAGGING);
                    StoriesPagerFragment frag1 = (StoriesPagerFragment)mActivitySampleBinding.viewPagerSample
                            .getAdapter()
                            .instantiateItem(mActivitySampleBinding.viewPagerSample, mActivitySampleBinding.viewPagerSample.getCurrentItem());
                    frag1.onPasue(mActivitySampleBinding.viewPagerSample.getCurrentItem());
                }
                if (state == ViewPager.SCROLL_STATE_SETTLING) {
                    Log.e("",""+state+"---ViewPager.SCROLL_STATE_SETTLING---"+ViewPager.SCROLL_STATE_SETTLING);
                }
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    Log.e("",""+state+"---ViewPager.SCROLL_STATE_IDLE---"+ViewPager.SCROLL_STATE_IDLE);
                    StoriesPagerFragment frag1 = (StoriesPagerFragment)mActivitySampleBinding.viewPagerSample
                            .getAdapter()
                            .instantiateItem(mActivitySampleBinding.viewPagerSample, mActivitySampleBinding.viewPagerSample.getCurrentItem());
                    frag1.onPlayStory(mActivitySampleBinding.viewPagerSample.getCurrentItem());
                }
            }
        };
        mActivitySampleBinding.viewPagerSample.addOnPageChangeListener(viewPagerPageChangeListener);
        mActivitySampleBinding.viewPagerSample.setCurrentItem(mActivitySampleBinding.viewPagerSample.getCurrentItem()+position);
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

    public void back(){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
