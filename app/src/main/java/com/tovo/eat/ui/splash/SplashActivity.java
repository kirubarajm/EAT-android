package com.tovo.eat.ui.splash;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;

import com.android.databinding.library.baseAdapters.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivitySplashBinding;
import com.tovo.eat.ui.account.feedbackandsupport.support.replies.RepliesActivity;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.onboarding.OnBoardingActivity;
import com.tovo.eat.ui.onboarding.PrefManager;
import com.tovo.eat.ui.signup.SignUpActivity;
import com.tovo.eat.ui.signup.namegender.NameGenderActivity;
import com.tovo.eat.ui.update.UpdateActivity;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;

import javax.inject.Inject;

public class SplashActivity extends BaseActivity<ActivitySplashBinding, SplashActivityViewModel>
        implements SplashActivityNavigator {

    @Inject
    SplashActivityViewModel mSplashActivityViewModel;

    private ActivitySplashBinding mActivitySplashBinding;
    private PrefManager prefManager;

    public static Intent newIntent(Context context) {
        return new Intent(context, SplashActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void checkForUserLoginMode(boolean trueOrFlase) {
        if (trueOrFlase) {
            Intent intent = MainActivity.newIntent(SplashActivity.this);
            startActivity(intent);
            finish();
        } else {
            Intent intent = SignUpActivity.newIntent(SplashActivity.this);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void update(boolean updateStatus, boolean forceUpdateStatus) {


        if (updateStatus) {
            Intent intent = UpdateActivity.newIntent(SplashActivity.this);
            intent.putExtra("forceUpdate", forceUpdateStatus);
            startActivity(intent);
            finish();
        } else {
            mSplashActivityViewModel.checkIsUserLoggedInOrNot();
        }


    }

    @Override
    public void checkForUserGenderStatus(boolean trueOrFalse) {
        Intent intent = NameGenderActivity.newIntent(SplashActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public int getBindingVariable() {
        return BR.splashViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public SplashActivityViewModel getViewModel() {
        return mSplashActivityViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivitySplashBinding = getViewDataBinding();
        mSplashActivityViewModel.setNavigator(this);


        prefManager = new PrefManager(this);



      /*  mSplashActivityViewModel.getDataManager().saveApiToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6IjkwOTQ5MzkzNDciLCJpYXQiOjE1NjYyMTEyNDZ9.jOg5m2fkw6U6dGyhKpNWn594N34deElh5kqKemXe_x8");


        mSplashActivityViewModel. getDataManager().updateEmailStatus(true);



            mSplashActivityViewModel.getDataManager().saveRegionId(0);



        mSplashActivityViewModel.getDataManager().updateUserInformation(127, null, null, null, null);

        Intent intent = MainActivity.newIntent(SplashActivity.this);
        startActivity(intent);
        finish();*/



    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterWifiReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerWifiReceiver();
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            if (null != intent.getExtras().getString("pageid") && intent.getExtras().getString("pageid").equals("9")) {

                Intent repliesIntent = RepliesActivity.newIntent(SplashActivity.this);
                startActivity(repliesIntent);
                finish();

            } else {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!prefManager.isFirstTimeLaunch()) {
                            Intent intent = OnBoardingActivity.newIntent(SplashActivity.this);
                            startActivity(intent);
                            finish();
                        } else {

                            mSplashActivityViewModel.checkUpdate();

                        }
                    }
                }, 1000);
            }
        }else {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!prefManager.isFirstTimeLaunch()) {
                        Intent intent = OnBoardingActivity.newIntent(SplashActivity.this);
                        startActivity(intent);
                        finish();
                    } else {

                        mSplashActivityViewModel.checkUpdate();

                    }
                }
            }, 1000);


        }
    }
    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //   if (mMainViewModel.isAddressAdded()) {
            if (checkWifiConnect()) {
            } else {
                Intent inIntent = InternetErrorFragment.newIntent(MvvmApp.getInstance());
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

    private  void unregisterWifiReceiver() {
        unregisterReceiver(mWifiReceiver);
    }



}
