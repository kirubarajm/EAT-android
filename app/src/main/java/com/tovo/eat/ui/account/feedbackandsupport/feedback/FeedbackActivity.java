package com.tovo.eat.ui.account.feedbackandsupport.feedback;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityFeedbackBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.signup.opt.OtpActivity;

import javax.inject.Inject;

public class FeedbackActivity extends BaseActivity<ActivityFeedbackBinding, FeedbackActivityViewModel> implements FeedbackActivityNavigator {

    @Inject
    FeedbackActivityViewModel mFeedbackAndSupportActivityViewModel;
    ActivityFeedbackBinding mActivityFeedbackBinding;
    int rate = 0;
    String message = "";

    public static Intent newIntent(Context context) {

        return new Intent(context, FeedbackActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void submit() {
        rate = (int) mActivityFeedbackBinding.rateApp.getRating();
        message = mActivityFeedbackBinding.edtFeedback.getText().toString();

        if (validForSubmit())
            mFeedbackAndSupportActivityViewModel.insertFeedbackServiceCall(rate, message);
    }

    @Override
    public void feedBackSucess(String strMessage) {
        mActivityFeedbackBinding.edtFeedback.setText("");
        mActivityFeedbackBinding.rateApp.setRating(0);
        Toast.makeText(getApplicationContext(), strMessage, Toast.LENGTH_SHORT).show();
        Intent intent = MainActivity.newIntent(FeedbackActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void feedBackFailure(String strMessage) {
        Toast.makeText(getApplicationContext(), strMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getBindingVariable() {
        return BR.feedbackViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    public FeedbackActivityViewModel getViewModel() {
        return mFeedbackAndSupportActivityViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityFeedbackBinding = getViewDataBinding();
        mFeedbackAndSupportActivityViewModel.setNavigator(this);

        Toolbar toolbar = findViewById(R.id.toolbar_feedback_support);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        mActivityFeedbackBinding.toolbarFeedbackSupport.setTitle("FeedBack and Support");

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

    private boolean validForSubmit() {
        if (rate == 0) {
            Toast.makeText(getApplicationContext(), "Please give rating", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mActivityFeedbackBinding.edtFeedback.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Please fill your feedback", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
