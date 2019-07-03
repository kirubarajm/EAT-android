package com.tovo.eat.ui.signup.tandc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;


import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityTermsAndConditionBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.registration.RegistrationActivity;

import javax.inject.Inject;

public class TermsAndConditionActivity extends BaseActivity<ActivityTermsAndConditionBinding, TermsAndConditionViewModel> implements TermsAndConditionNavigator {

    @Inject
    TermsAndConditionViewModel mTermsAndConditionModel;
    ActivityTermsAndConditionBinding mActivityTermsAndConditionBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, TermsAndConditionActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.termsViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_terms_and_condition;
    }

    @Override
    public TermsAndConditionViewModel getViewModel() {
        return mTermsAndConditionModel;
    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void openRegActivity() {
        Intent intent = RegistrationActivity.newIntent(TermsAndConditionActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityTermsAndConditionBinding = getViewDataBinding();
        mTermsAndConditionModel.setNavigator(this);

        setSupportActionBar(mActivityTermsAndConditionBinding.toolbar1);

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
