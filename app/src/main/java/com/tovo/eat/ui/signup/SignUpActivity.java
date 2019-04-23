package com.tovo.eat.ui.signup;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivitySignupBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.signup.opt.OtpActivity;

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
       /* String strPhoneNumber = mActivitySignupBinding.edtPhoneNo.getText().toString();
        String strPassword = mActivitySignupBinding.edtPassword.getText().toString();
        if (mLoginViewModelMain.isEmailAndPasswordValid(strPhoneNumber, strPassword)) {
            hideKeyboard();
            //mLoginViewModelMain.users(strPhoneNumber, strPassword);
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.invalidEmail), Toast.LENGTH_SHORT).show();
        }*/

        Intent intent = OtpActivity.newIntent(SignUpActivity.this);
        intent.putExtra("key","value");
        startActivity(intent);
        finish();

    }

    @Override
    public void openLoginMainActivity() {
        Intent intent = MainActivity.newIntent(SignUpActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginSuccess(String strSucess) {
       /* if (strSucess.equalsIgnoreCase(AppConstants.TRUE)) {
            openLoginMainActivity();
            Toast.makeText(getApplicationContext(), AppConstants.LOGIN_SUCCESS, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), AppConstants.INVALID_CREDENTIALS, Toast.LENGTH_SHORT).show();
        }*/
    }

    @Override
    public void loginError(String strError) {
        /*if (strError!=null && !strError.equals("")) {
            if (strError.equalsIgnoreCase(AppConstants.TRUE)) {
                Toast.makeText(getApplicationContext(), AppConstants.INVALID_CREDENTIALS, Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(getApplicationContext(), AppConstants.LOGIN_FAILED, Toast.LENGTH_LONG).show();
            }
        }*/
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
        requestPermissionsSafely(new String[]{Manifest.permission.CALL_PHONE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
