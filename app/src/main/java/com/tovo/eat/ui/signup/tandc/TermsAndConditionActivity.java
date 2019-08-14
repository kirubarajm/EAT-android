package com.tovo.eat.ui.signup.tandc;

import android.app.Activity;
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
    public void goBack() {
        finish();
    }

    @Override
    public void accept() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityTermsAndConditionBinding = getViewDataBinding();
        mTermsAndConditionModel.setNavigator(this);




        mActivityTermsAndConditionBinding.webview.getSettings().setJavaScriptEnabled(true);
        mActivityTermsAndConditionBinding.webview.loadUrl("file:///android_asset/terms.html");


    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
