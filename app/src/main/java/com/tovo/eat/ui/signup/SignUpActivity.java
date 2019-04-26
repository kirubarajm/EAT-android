package com.tovo.eat.ui.signup;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivitySignupBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.signup.namegender.NameGenderActivity;
import com.tovo.eat.ui.signup.opt.OtpActivity;
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
    public void usersLoginMain() {
        /*Intent intent = OtpActivity.newIntent(SignUpActivity.this);
        intent.putExtra("key","value");
        startActivity(intent);
        finish();*/
        String strPhoneNumber = mActivitySignupBinding.edtPhoneNo.getText().toString();
        /*if (validForMobileNo()) {*/
        mLoginViewModelMain.users(strPhoneNumber);
            /*Intent intent = OtpActivity.newIntent(SignUpActivity.this);
            startActivity(intent);
            finish();*/
        /*  }*/
    }

    @Override
    public void loginSuccess(boolean passwordstatus, boolean otpStatus, boolean genderstatus) {
       /* if (strSucess.equalsIgnoreCase(AppConstants.TRUE)) {
            openMainActivity();
            Toast.makeText(getApplicationContext(), AppConstants.LOGIN_SUCCESS, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), AppConstants.INVALID_CREDENTIALS, Toast.LENGTH_SHORT).show();
        }*/

        if (passwordstatus) {

        }
        if (otpStatus) {

        }
        if (genderstatus) {

        }
    }

    @Override
    public void loginError(boolean strError) {
        /*if (strError!=null && !strError.equals("")) {
            if (strError.equalsIgnoreCase(AppConstants.TRUE)) {
                Toast.makeText(getApplicationContext(), AppConstants.INVALID_CREDENTIALS, Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(getApplicationContext(), AppConstants.LOGIN_FAILED, Toast.LENGTH_LONG).show();
            }
        }*/
    }

    @Override
    public void otpScreenFalse(boolean trurOrFalse,int opt) {
        String strPhoneNumber = mActivitySignupBinding.edtPhoneNo.getText().toString();
        Intent intent = OtpActivity.newIntent(SignUpActivity.this);
        intent.putExtra("booleanOpt",String.valueOf(trurOrFalse));
        intent.putExtra("optId",String.valueOf(opt));
        intent.putExtra("strPhoneNumber",strPhoneNumber);
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
        requestPermissionsSafely(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
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
