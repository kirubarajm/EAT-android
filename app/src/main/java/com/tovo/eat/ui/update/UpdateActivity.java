package com.tovo.eat.ui.update;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.android.databinding.library.baseAdapters.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivitySplashBinding;
import com.tovo.eat.databinding.ActivityUpdateBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.onboarding.PrefManager;
import com.tovo.eat.ui.signup.SignUpActivity;
import com.tovo.eat.ui.signup.namegender.NameGenderActivity;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.analytics.Analytics;

import javax.inject.Inject;

public class UpdateActivity extends BaseActivity<ActivityUpdateBinding, UpdateViewModel>
        implements UpdateNavigator {

    @Inject
    UpdateViewModel mUpdateViewModel;

    private ActivityUpdateBinding mActivityUpdateBinding;
    private PrefManager prefManager;


    Analytics analytics;
    String  pageName=AppConstants.SCREEN_FORCE_UPDATE;


    public static Intent newIntent(Context context) {
        return new Intent(context, UpdateActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void checkForUserLoginMode(boolean trueOrFlase) {
        if (trueOrFlase) {
            Intent intent = MainActivity.newIntent(UpdateActivity.this);
            startActivity(intent);
            finish();
        } else {
            Intent intent = SignUpActivity.newIntent(UpdateActivity.this);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void checkForUserGenderStatus(boolean trueOrFalse) {
        Intent intent = NameGenderActivity.newIntent(UpdateActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void update() {

        new Analytics().sendClickData(AppConstants.SCREEN_FORCE_UPDATE, AppConstants.CLICK_UPDATE);


        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
        finish();

    }


    @Override
    public void onBackPressed() {
        new Analytics().sendClickData(AppConstants.SCREEN_FORCE_UPDATE, AppConstants.CLICK_BACK_BUTTON);
        super.onBackPressed();

    }

    @Override
    public int getBindingVariable() {
        return BR.updateViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_update;
    }

    @Override
    public UpdateViewModel getViewModel() {
        return mUpdateViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityUpdateBinding = getViewDataBinding();
        mUpdateViewModel.setNavigator(this);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {

            mUpdateViewModel.forceUpdate.set(intent.getExtras().getBoolean("forceUpdate"));

        }



        analytics=new Analytics(this,pageName);
    }

    @Override
    public void onFragmentDetached(String tag) {

    }

}
