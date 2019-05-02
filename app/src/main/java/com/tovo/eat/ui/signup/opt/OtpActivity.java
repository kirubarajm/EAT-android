package com.tovo.eat.ui.signup.opt;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityOtpBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.forgotpassword.ForgotPasswordActivity;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.signup.SignUpActivity;
import com.tovo.eat.ui.signup.namegender.NameGenderActivity;
import com.tovo.eat.utilities.AppConstants;

import javax.inject.Inject;

public class OtpActivity extends BaseActivity<ActivityOtpBinding, OtpActivityViewModel> implements OtpActivityNavigator {

    @Inject
    OtpActivityViewModel mLoginViewModelMain;
    String strPhoneNumber = "";
    String strOtpId = "";
    String UserId = "";
    private ActivityOtpBinding mActivityOtpBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, OtpActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void continueClick() {
        //hideKeyboard();
        //strPhoneNumber=mActivityOtpBinding.edtPassword.getText().toString();
        if (validForOtp())
            mLoginViewModelMain.userContinueClick(strPhoneNumber, 12345, Integer.parseInt(strOtpId));
    }

    @Override
    public void openHomeActivity(boolean trueOrFalse) {
        if (trueOrFalse) {
            Toast.makeText(getApplicationContext(), AppConstants.TOAST_LOGIN_SUCCESS, Toast.LENGTH_SHORT).show();
            Intent intent = MainActivity.newIntent(OtpActivity.this);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), AppConstants.TOAST_LOGIN_FAILED, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void nameGenderScreen() {
        Intent intent = NameGenderActivity.newIntent(OtpActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void login() {
        if (validForPassword()) {
            String strPassword = mActivityOtpBinding.edtPassword.getText().toString();
            mLoginViewModelMain.login(strPhoneNumber, strPassword);
        }
    }

    @Override
    public void forgotPassword() {
        Intent intent = ForgotPasswordActivity.newIntent(OtpActivity.this);
        intent.putExtra("strPhoneNumber", strPhoneNumber);
        startActivity(intent);
    }

    @Override
    public void loginSuccess() {
        Toast.makeText(getApplicationContext(), AppConstants.TOAST_LOGIN_SUCCESS, Toast.LENGTH_SHORT).show();
        Intent intent = MainActivity.newIntent(OtpActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginFailure() {
        Toast.makeText(getApplicationContext(), AppConstants.TOAST_LOGIN_FAILED, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getBindingVariable() {
        return BR.otpViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_otp;
    }

    @Override
    public OtpActivityViewModel getViewModel() {
        return mLoginViewModelMain;
    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityOtpBinding = getViewDataBinding();
        mLoginViewModelMain.setNavigator(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String booleanOtp = bundle.getString("booleanOpt");
            strOtpId = bundle.getString("optId");
            strPhoneNumber = bundle.getString("strPhoneNumber");
            UserId = bundle.getString("UserId");
            if (booleanOtp.equalsIgnoreCase("true")) {
                mLoginViewModelMain.otp.set(true);
                Toolbar toolbar = findViewById(R.id.toolbar_otp);
                setSupportActionBar(toolbar);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setDisplayShowHomeEnabled(true);
                }
                toolbar.setTitle("Login");

            } else {
                mLoginViewModelMain.otp.set(false);
                Toolbar toolbar = findViewById(R.id.toolbar_otp);
                setSupportActionBar(toolbar);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setDisplayShowHomeEnabled(true);
                }
                toolbar.setTitle("OTP");
            }
            mActivityOtpBinding.txtMessageSent.setText("Message Sent to " + strPhoneNumber);

        }

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = SignUpActivity.newIntent(OtpActivity.this);
        startActivity(intent);
        finish();
        return true;
    }

    private boolean validForOtp() {
        if (mActivityOtpBinding.edt1.getText().toString().equals("") && mActivityOtpBinding.edt2.getText().toString().equals("") && mActivityOtpBinding.edt4.getText().toString().equals("")
                && mActivityOtpBinding.edt4.getText().toString().equals("") && mActivityOtpBinding.edt5.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), AppConstants.TOAST_ENTER_OTP, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validForPassword() {
        if (mActivityOtpBinding.edtPassword.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), AppConstants.TOAST_ENTER_PASSWORD, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
