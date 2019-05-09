package com.tovo.eat.ui.splash;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.android.databinding.library.baseAdapters.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivitySplashBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.onboarding.OnBoardingActivity;
import com.tovo.eat.ui.onboarding.PrefManager;
import com.tovo.eat.ui.signup.SignUpActivity;
import com.tovo.eat.ui.signup.namegender.NameGenderActivity;

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


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               if (!prefManager.isFirstTimeLaunch()) {
                    Intent intent = OnBoardingActivity.newIntent(SplashActivity.this);
                    startActivity(intent);
                    finish();
                }else {

                mSplashActivityViewModel.checkIsUserLoggedInOrNot();

               }
            }
        },1000);

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

}
