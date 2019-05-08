package com.tovo.eat.ui.account.feedbackandsupport;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityFeedbackSupportBinding;
import com.tovo.eat.ui.account.feedbackandsupport.feedback.FeedbackActivity;
import com.tovo.eat.ui.account.feedbackandsupport.support.SupportActivity;
import com.tovo.eat.ui.base.BaseActivity;

import javax.inject.Inject;

public class FeedbackAndSupportActivity extends BaseActivity<ActivityFeedbackSupportBinding, FeedbackAndSupportActivityViewModel> implements FeedbackAndSupportActivityNavigator {

    @Inject
    FeedbackAndSupportActivityViewModel mFeedbackAndSupportActivityViewModel;
    ActivityFeedbackSupportBinding mActivityFeedbackSupportBinding;


    public static Intent newIntent(Context context) {

        return new Intent(context, FeedbackAndSupportActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void feedBackClick() {
        Intent intent = FeedbackActivity.newIntent(this);
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
    public int getBindingVariable() {
        return BR.feedBackSupportViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback_support;
    }

    @Override
    public FeedbackAndSupportActivityViewModel getViewModel() {
        return mFeedbackAndSupportActivityViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFeedbackAndSupportActivityViewModel.setNavigator(this);
        mActivityFeedbackSupportBinding = getViewDataBinding();



    }

}
