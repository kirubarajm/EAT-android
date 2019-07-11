package com.tovo.eat.ui.signup.fagsandsupport;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityFaqsSupportBinding;
import com.tovo.eat.ui.account.feedbackandsupport.support.SupportActivity;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.signup.faqs.FaqActivity;

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

        String number = "9999999999";

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + Uri.encode(number.trim())));
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(callIntent);


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

}
