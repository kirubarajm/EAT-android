package com.tovo.eat.ui.forgotpassword;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.databinding.library.baseAdapters.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityForgotPasswordBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.utilities.AppConstants;

import javax.inject.Inject;

public class ForgotPasswordActivity extends BaseActivity<ActivityForgotPasswordBinding, ForgotPasswordActivityViewModel>
        implements ForgotPasswordActivityNavigator {

    String strPhoneNumber = "";
    @Inject
    ForgotPasswordActivityViewModel mForgotPasswordActivityViewModel;

    private ActivityForgotPasswordBinding mActivityForgotPasswordBinding;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("otp")) {
                final String message = intent.getStringExtra("message");
                mActivityForgotPasswordBinding.edt1.setText(message.substring(0, 1));
                mActivityForgotPasswordBinding.edt2.setText(message.substring(1, 2));
                mActivityForgotPasswordBinding.edt3.setText(message.substring(2, 3));
                mActivityForgotPasswordBinding.edt4.setText(message.substring(3, 4));
                mActivityForgotPasswordBinding.edt5.setText(message.substring(4, 5));
            }
        }
    };

    public static Intent newIntent(Context context) {
        return new Intent(context, ForgotPasswordActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void otpVerificationProceed() {
        String str1 = mActivityForgotPasswordBinding.edt1.getText().toString();
        String str2 = mActivityForgotPasswordBinding.edt2.getText().toString();
        String str3 = mActivityForgotPasswordBinding.edt3.getText().toString();
        String str4 = mActivityForgotPasswordBinding.edt4.getText().toString();
        String str5 = mActivityForgotPasswordBinding.edt5.getText().toString();
        String strOtp = str1 + str2 + str3 + str4 + str5;
        mForgotPasswordActivityViewModel.otpVerificationServiceCall(strPhoneNumber, strOtp);
    }

    @Override
    public void otpVerificationSubmit() {
        if (validForPass()) {
            String strpass = mActivityForgotPasswordBinding.edtConfirmPassword.getText().toString();
            mForgotPasswordActivityViewModel.passwordVerificationServiceCall(strpass);
        }
    }

    @Override
    public void confirmPassSuccess() {
        mActivityForgotPasswordBinding.edtPassword.setText("");
        mActivityForgotPasswordBinding.edtConfirmPassword.setText("");
        Toast.makeText(getApplicationContext(), "Password changed successfully", Toast.LENGTH_SHORT).show();
        this.finish();
    }

    @Override
    public void confirmPassFailre() {
        Toast.makeText(getApplicationContext(), "Password not changed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void otpInvalid() {
        Toast.makeText(getApplicationContext(), "Invalid Otp", Toast.LENGTH_SHORT).show();
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
        return mForgotPasswordActivityViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityForgotPasswordBinding = getViewDataBinding();
        mForgotPasswordActivityViewModel.setNavigator(this);


        Toolbar toolbar = findViewById(R.id.toolbar_forgot_pass);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        mActivityForgotPasswordBinding.toolbarForgotPass.setTitle("Forgot Password");

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            strPhoneNumber = bundle.getString("strPhoneNumber");
            mForgotPasswordActivityViewModel.otpServiceCall(strPhoneNumber);
        }
    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    private boolean validForPass() {
        String pass = mActivityForgotPasswordBinding.edtPassword.getText().toString();
        String cPass = mActivityForgotPasswordBinding.edtConfirmPassword.getText().toString();

        if (mActivityForgotPasswordBinding.edtPassword.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), AppConstants.TOAST_ENTER_PASSWORD, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mActivityForgotPasswordBinding.edtConfirmPassword.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), AppConstants.TOAST_ENTER_RE_ENTER_PASSWORD, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!pass.equals(cPass)) {
            Toast.makeText(getApplicationContext(), AppConstants.TOAST_PASSWORD_NOT_MATCHING, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
