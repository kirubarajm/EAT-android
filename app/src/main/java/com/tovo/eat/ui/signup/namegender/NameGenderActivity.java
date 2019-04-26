package com.tovo.eat.ui.signup.namegender;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityNameGenderBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.signup.SignUpActivity;
import com.tovo.eat.utilities.AppConstants;

import javax.inject.Inject;

public class NameGenderActivity extends BaseActivity<ActivityNameGenderBinding, NameGenderActivityViewModel>
        implements NameGenderActivityNavigator {

    @Inject
    NameGenderActivityViewModel mLoginViewModelMain;
    boolean aBooleanAlpha = true;
    private ActivityNameGenderBinding mActivityNameGenderBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, NameGenderActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void proceedClick() {
        String name = mActivityNameGenderBinding.edtName.getText().toString();
        if (validForProceed())
            mLoginViewModelMain.insertNameGenderServiceCall(5, name, 1);
    }

    @Override
    public void openActivity(String strMessage) {
        Toast.makeText(getApplicationContext(), strMessage, Toast.LENGTH_SHORT).show();
        Intent intent = MainActivity.newIntent(NameGenderActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public int getBindingVariable() {
        return BR.nameGenderViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_name_gender;
    }

    @Override
    public NameGenderActivityViewModel getViewModel() {
        return mLoginViewModelMain;
    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityNameGenderBinding = getViewDataBinding();
        mLoginViewModelMain.setNavigator(this);
        genderClick();
    }

    private void genderClick() {
        mActivityNameGenderBinding.relMale.setAlpha(1);
        mActivityNameGenderBinding.relFemale.setAlpha(.5f);
        mActivityNameGenderBinding.relMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivityNameGenderBinding.relMale.setAlpha(1);
                mActivityNameGenderBinding.relFemale.setAlpha(.5f);
            }
        });
        mActivityNameGenderBinding.relFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivityNameGenderBinding.relMale.setAlpha(.5f);
                mActivityNameGenderBinding.relFemale.setAlpha(1);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = SignUpActivity.newIntent(NameGenderActivity.this);
        startActivity(intent);
        finish();
        return true;
    }

    private boolean validForProceed() {
        if (mActivityNameGenderBinding.edtName.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), AppConstants.TOAST_ENTER_NAME, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
