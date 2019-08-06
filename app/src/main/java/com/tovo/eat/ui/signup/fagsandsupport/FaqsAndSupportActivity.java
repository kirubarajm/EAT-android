package com.tovo.eat.ui.signup.fagsandsupport;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityFaqsSupportBinding;
import com.tovo.eat.ui.account.feedbackandsupport.support.SupportActivity;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.signup.faqs.FaqActivity;
import com.tovo.eat.utilities.AppConstants;

import javax.inject.Inject;

public class FaqsAndSupportActivity extends BaseActivity<ActivityFaqsSupportBinding, FaqsAndSupportViewModel> implements FaqsAndSupportNavigator {

    @Inject
    FaqsAndSupportViewModel mFaqsAndSupportViewModel;
    ActivityFaqsSupportBinding mActivityFaqsSupportBinding;


    public static Intent newIntent(Context context) {

        return new Intent(context, FaqsAndSupportActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void feedBackClick() {
        Intent intent = FaqActivity.newIntent(this);
        startActivity(intent);
    }

    @Override
    public void supportClick() {
        Intent intent = SupportActivity.newIntent(this);
        startActivity(intent);
    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public void callCusstomerCare() {


        if (ContextCompat.checkSelfPermission(FaqsAndSupportActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(FaqsAndSupportActivity.this, new String[]{Manifest.permission.CALL_PHONE},AppConstants.CALL_PHONE_PERMISSION_REQUEST_CODE);
        }
        else
        {
            String number = "9597352662";
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + Uri.encode(number.trim())));
            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(callIntent);
        }

    }

    @Override
    public int getBindingVariable() {
        return BR.faqsAndSupportViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_faqs_support;
    }

    @Override
    public FaqsAndSupportViewModel getViewModel() {
        return mFaqsAndSupportViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFaqsAndSupportViewModel.setNavigator(this);
        mActivityFaqsSupportBinding = getViewDataBinding();


    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case AppConstants.CALL_PHONE_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // callAdmin();
                    String number = "9597352662";
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + Uri.encode(number.trim())));
                    callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(callIntent);

                } else{
                    String number = "9597352662";
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + Uri.encode(number.trim())));
                    callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(callIntent);
                }
                return;
            }
        }
    }
}
