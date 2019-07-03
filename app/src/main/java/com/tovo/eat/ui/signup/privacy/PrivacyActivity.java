package com.tovo.eat.ui.signup.privacy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityPrivacyBinding;
import com.tovo.eat.databinding.ActivityTermsAndConditionBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.registration.RegistrationActivity;

import javax.inject.Inject;

public class PrivacyActivity extends BaseActivity<ActivityPrivacyBinding, PrivacyViewModel> implements PrivacyNavigator {

    @Inject
    PrivacyViewModel mPrivacyViewModel;
    ActivityPrivacyBinding mActivityPrivacyBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, PrivacyActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.privacyViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_privacy;
    }

    @Override
    public PrivacyViewModel getViewModel() {
        return mPrivacyViewModel;
    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void openRegActivity() {
        Intent intent = RegistrationActivity.newIntent(PrivacyActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityPrivacyBinding = getViewDataBinding();
        mPrivacyViewModel.setNavigator(this);


        setSupportActionBar(mActivityPrivacyBinding.toolbar1);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onBackPressed() {
       super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
