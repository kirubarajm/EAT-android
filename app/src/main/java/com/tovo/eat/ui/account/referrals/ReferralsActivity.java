package com.tovo.eat.ui.account.referrals;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.tovo.eat.BR;
import com.tovo.eat.BuildConfig;
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
    public void sendReferralsClick() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
            String shareMessage = "Let me recommend you this application";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) {
            e.toString();
        }
    }

    @Override
    public int getBindingVariable() {
        return BR.referralsViewModel;
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
