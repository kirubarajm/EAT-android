package com.tovo.eat.ui.forgotpassword;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.android.databinding.library.baseAdapters.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityForgotPasswordBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.signup.SignUpActivity;

import javax.inject.Inject;

public class ForgotPasswordActivity extends BaseActivity<ActivityForgotPasswordBinding, ForgotPasswordActivityViewModel>
        implements ForgotPasswordActivityNavigator {

    @Inject
    ForgotPasswordActivityViewModel mSplashActivityViewModel;

    private ActivityForgotPasswordBinding mActivityForgotPasswordBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, ForgotPasswordActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void checkForUserLoginMode(boolean trueOrFlase) {
        if (trueOrFlase) {
            Intent intent = MainActivity.newIntent(ForgotPasswordActivity.this);
            startActivity(intent);
            finish();
        } else {
            Intent intent = SignUpActivity.newIntent(ForgotPasswordActivity.this);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public int getBindingVariable() {
        return BR.forgotPasswordViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_forgot_password;
    }

    @Override
    public ForgotPasswordActivityViewModel getViewModel() {
        return mSplashActivityViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityForgotPasswordBinding = getViewDataBinding();
        mSplashActivityViewModel.setNavigator(this);
        mSplashActivityViewModel.checkIsUserLoggedInOrNot();
    }

    @Override
    public void onFragmentDetached(String tag) {

    }

}
