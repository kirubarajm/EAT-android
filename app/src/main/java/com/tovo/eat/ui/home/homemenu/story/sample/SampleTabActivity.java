package com.tovo.eat.ui.home.homemenu.story.sample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivitySampleBinding;
import com.tovo.eat.ui.account.favorites.favdish.CartFavListener;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.home.homemenu.story.StoriesResponse;
import com.tovo.eat.ui.home.homemenu.story.library.CubeTransformer;
import com.tovo.eat.ui.home.homemenu.story.library.StatusStoriesFragment;
import com.tovo.eat.ui.home.homemenu.story.library.StoryStatusView;
import com.tovo.eat.ui.home.homemenu.story.sample.fragment.SamplePagerFragment;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;

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

    int position=0,crPosition=0;
    StoriesResponse storiesFullResponse;

    public static Intent newIntent(Context context) {

        return new Intent(context, SampleTabActivity.class);
    }

    public void methodDDD(int pos){
        if (pos==position) {
        SamplePagerFragment frag1 = (SamplePagerFragment)mActivitySampleBinding.viewPagerSample
                .getAdapter()
                .instantiateItem(mActivitySampleBinding.viewPagerSample, mActivitySampleBinding.viewPagerSample.getCurrentItem());
        frag1.onPlayStorie(position);
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
                SamplePagerFragment frag1 = (SamplePagerFragment)mActivitySampleBinding.viewPagerSample
                        .getAdapter()
                        .instantiateItem(mActivitySampleBinding.viewPagerSample, mActivitySampleBinding.viewPagerSample.getCurrentItem());
                frag1.onPlayStorie(positions);

                SamplePagerFragment fragmentToPrev = (SamplePagerFragment)mActivitySampleBinding.viewPagerSample.getAdapter().instantiateItem(mActivitySampleBinding.viewPagerSample, crPosition);
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
                    SamplePagerFragment frag1 = (SamplePagerFragment)mActivitySampleBinding.viewPagerSample
                            .getAdapter()
                            .instantiateItem(mActivitySampleBinding.viewPagerSample, mActivitySampleBinding.viewPagerSample.getCurrentItem());
                    frag1.onPasue(mActivitySampleBinding.viewPagerSample.getCurrentItem());

                }
                if (state == ViewPager.SCROLL_STATE_SETTLING) {
                    Log.e("",""+state+"---ViewPager.SCROLL_STATE_SETTLING---"+ViewPager.SCROLL_STATE_SETTLING);
                }
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    Log.e("",""+state+"---ViewPager.SCROLL_STATE_IDLE---"+ViewPager.SCROLL_STATE_IDLE);
                    SamplePagerFragment frag1 = (SamplePagerFragment)mActivitySampleBinding.viewPagerSample
                            .getAdapter()
                            .instantiateItem(mActivitySampleBinding.viewPagerSample, mActivitySampleBinding.viewPagerSample.getCurrentItem());
                    frag1.onPlayStorie(mActivitySampleBinding.viewPagerSample.getCurrentItem());
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
        registerWifiReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterWifiReceiver();
    }

    public void back(){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



    private void registerWifiReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mWifiReceiver, filter);
    }


    private  boolean checkWifiConnect() {
        ConnectivityManager manager = (ConnectivityManager) MvvmApp.getInstance(). getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();


        ConnectivityManager cm =
                (ConnectivityManager) MvvmApp.getInstance() .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();


        if (networkInfo != null
                && networkInfo.getType() == ConnectivityManager.TYPE_WIFI
                && networkInfo.isConnected()) {
            return true;
        } else return networkInfo != null
                && networkInfo.isConnected();
    }

    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //   if (mMainViewModel.isAddressAdded()) {
            if (checkWifiConnect()) {
            } else {
                Intent inIntent= InternetErrorFragment.newIntent(MvvmApp.getInstance());
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(inIntent);
               /* FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                InternetErrorFragment fragment = new InternetErrorFragment();
                transaction.replace(R.id.content_main, fragment);
                transaction.commit();
                internetCheck = true;*/
            }
        }
    };
    private  void unregisterWifiReceiver() {
        unregisterReceiver(mWifiReceiver);
    }



}
