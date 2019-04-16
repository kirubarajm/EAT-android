package com.tovo.eat.ui.account.referrals;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityReferralsBinding;
import com.tovo.eat.ui.base.BaseActivity;

import javax.inject.Inject;

public class ReferralsActivity extends BaseActivity<ActivityReferralsBinding, ReferralsActivityViewModel> implements ReferralsActivityNavigator {

    @Inject
    ReferralsActivityViewModel mFeedbackAndSupportActivityViewModel;
    ActivityReferralsBinding mActivityReferralsBinding;

    public static Intent newIntent(Context context) {

        return new Intent(context, ReferralsActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public int getBindingVariable() {
        return BR.feedBackSupportViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_referrals;
    }

    @Override
    public ReferralsActivityViewModel getViewModel() {
        return mFeedbackAndSupportActivityViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFeedbackAndSupportActivityViewModel.setNavigator(this);
        mActivityReferralsBinding = getViewDataBinding();

        Toolbar toolbar = findViewById(R.id.toolbar_referrals);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        mActivityReferralsBinding.toolbarReferrals.setTitle("Referrals");

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

}
