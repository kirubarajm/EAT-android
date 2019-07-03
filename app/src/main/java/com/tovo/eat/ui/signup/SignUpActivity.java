package com.tovo.eat.ui.signup;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivitySignupBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.signup.faqs.FaqActivity;
import com.tovo.eat.ui.signup.namegender.NameGenderActivity;
import com.tovo.eat.ui.signup.opt.OtpActivity;
import com.tovo.eat.ui.signup.privacy.PrivacyActivity;
import com.tovo.eat.ui.signup.tandc.TermsAndConditionActivity;
import com.tovo.eat.utilities.AppConstants;

import javax.inject.Inject;

public class SignUpActivity extends BaseActivity<ActivitySignupBinding, SignUpActivityViewModel>
        implements SignUpActivityNavigator {

    @Inject
    SignUpActivityViewModel mLoginViewModelMain;
    private ActivitySignupBinding mActivitySignupBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, SignUpActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void verifyUser() {
        /*Intent intent = OtpActivity.newIntent(SignUpActivity.this);
        intent.putExtra("key","value");
        startActivity(intent);
        finish();*/

        if (validForMobileNo()) {
            String strPhoneNumber = mActivitySignupBinding.edtPhoneNo.getText().toString();
            mLoginViewModelMain.users(strPhoneNumber);
            /*Intent intent = OtpActivity.newIntent(SignUpActivity.this);
            startActivity(intent);
            finish();*/
        }
    }

    @Override
    public void faqs() {
        Intent intent = FaqActivity.newIntent(SignUpActivity.this);
        startActivity(intent);

    }

    @Override
    public void privacy() {
        Intent intent = PrivacyActivity.newIntent(SignUpActivity.this);
        startActivity(intent);

    }

    @Override
    public void termsandconditions() {
        Intent intent = TermsAndConditionActivity.newIntent(SignUpActivity.this);
        startActivity(intent);

    }

    @Override
    public void loginError(boolean strError) {
        Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void otpScreenFalse(boolean trurOrFalse, int opt,int UserId) {
        String strPhoneNumber = mActivitySignupBinding.edtPhoneNo.getText().toString();
        Intent intent = OtpActivity.newIntent(SignUpActivity.this);
        intent.putExtra("booleanOpt", String.valueOf(trurOrFalse));
        intent.putExtra("optId", String.valueOf(opt));
        intent.putExtra("strPhoneNumber", strPhoneNumber);
        intent.putExtra("UserId", UserId);
        startActivity(intent);
        finish();
    }

    @Override
    public void genderScreenFalse(boolean passwordSuccess) {
        Intent intent = NameGenderActivity.newIntent(SignUpActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void openHomeScreen(boolean passwordSuccess) {
        Intent intent = MainActivity.newIntent(SignUpActivity.this);
        startActivity(intent);
        finish();

    }

    @Override
    public int getBindingVariable() {
        return BR.signup;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_signup;
    }

    @Override
    public SignUpActivityViewModel getViewModel() {
        return mLoginViewModelMain;
    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivitySignupBinding = getViewDataBinding();
        mLoginViewModelMain.setNavigator(this);
        FirebaseAnalytics.getInstance(this);
        requestPermissionsSafely(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_SMS,Manifest.permission.RECEIVE_SMS}, 0);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private boolean validForMobileNo() {
        if (mActivitySignupBinding.edtPhoneNo.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), AppConstants.TOAST_ENTER_MOBILE_NO, Toast.LENGTH_LONG).show();
            return false;
        }

        if (mActivitySignupBinding.edtPhoneNo.getText().length() < 10) {
            Toast.makeText(getApplicationContext(), AppConstants.TOAST_ENTER_VALID_MOBILE_NO, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


}
