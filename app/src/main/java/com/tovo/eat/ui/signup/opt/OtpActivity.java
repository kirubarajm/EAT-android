package com.tovo.eat.ui.signup.opt;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityOtpBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.signup.SignUpActivity;

import javax.inject.Inject;

public class OtpActivity extends BaseActivity<ActivityOtpBinding, OtpActivityViewModel>
        implements OtpActivityNavigator {

    @Inject
    OtpActivityViewModel mLoginViewModelMain;
    private ActivityOtpBinding mActivityOtpBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, OtpActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void usersLoginMain() {
       /* String strPhoneNumber = mActivityOtpBinding.edtPhoneNo.getText().toString();
        String strPassword = mActivityOtpBinding.edtPassword.getText().toString();
        if (mLoginViewModelMain.isEmailAndPasswordValid(strPhoneNumber, strPassword)) {
            hideKeyboard();
            //mLoginViewModelMain.users(strPhoneNumber, strPassword);
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.invalidEmail), Toast.LENGTH_SHORT).show();
        }*/

        openLoginMainActivity();
    }

    @Override
    public void openLoginMainActivity() {
        Intent intent = MainActivity.newIntent(OtpActivity.this);
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
        if (bundle!=null) {
            String strKey = bundle.getString("key");
        }
        Toolbar toolbar = findViewById(R.id.toolbar_otp);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setTitle("OTP");
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
}
