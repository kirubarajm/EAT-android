package com.tovo.eat.ui.signup;


import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivitySignupBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.signup.fagsandsupport.FaqsAndSupportActivity;
import com.tovo.eat.ui.signup.namegender.NameGenderActivity;
import com.tovo.eat.ui.signup.opt.OtpActivity;
import com.tovo.eat.ui.signup.privacy.PrivacyActivity;
import com.tovo.eat.ui.signup.tandc.TermsAndConditionActivity;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;

import javax.inject.Inject;

public class SignUpActivity extends BaseActivity<ActivitySignupBinding, SignUpActivityViewModel>
        implements SignUpActivityNavigator {

    public boolean continueClicked = false;
    @Inject
    SignUpActivityViewModel mLoginViewModelMain;
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
    private ActivitySignupBinding mActivitySignupBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, SignUpActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void verifyUser() {

        if (!continueClicked) {

            if (validForMobileNo()) {

                if (mActivitySignupBinding.acceptTandC.isChecked()) {
                    String strPhoneNumber = mActivitySignupBinding.edtPhoneNo.getText().toString();
                    mLoginViewModelMain.users(strPhoneNumber);
                    continueClicked = true;
                } else {

                    Toast.makeText(this, "Accept terms and conditions and continue", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    @Override
    public void faqs() {
        Intent intent = FaqsAndSupportActivity.newIntent(SignUpActivity.this);
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
        startActivityForResult(intent, AppConstants.TERMS_AND_CONDITION_REQUEST_CODE);
    }

    @Override
    public void loginError(boolean strError) {
        continueClicked = false;
        Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void otpScreenFalse(boolean trurOrFalse, int opt, int UserId) {
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
        requestPermissionsSafely(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.ACCESS_FINE_LOCATION}, 0);


        mActivitySignupBinding.edtPhoneNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length()==10){
                    hideKeyboard();
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == AppConstants.TERMS_AND_CONDITION_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                mActivitySignupBinding.acceptTandC.setChecked(true);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                mActivitySignupBinding.acceptTandC.setChecked(false);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        continueClicked = false;
        registerWifiReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterWifiReceiver();
    }

    private void registerWifiReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mWifiReceiver, filter);
    }

    private boolean checkWifiConnect() {
        ConnectivityManager manager = (ConnectivityManager) MvvmApp.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();


        ConnectivityManager cm =
                (ConnectivityManager) MvvmApp.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);

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

    private void unregisterWifiReceiver() {
        unregisterReceiver(mWifiReceiver);
    }


}
