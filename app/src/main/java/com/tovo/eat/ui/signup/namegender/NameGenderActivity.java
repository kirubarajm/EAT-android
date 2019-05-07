package com.tovo.eat.ui.signup.namegender;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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
    int gender;
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
            mLoginViewModelMain.insertNameGenderServiceCall(name, gender);
    }

    @Override
    public void genderSuccess(String strMessage) {
        Toast.makeText(getApplicationContext(), strMessage, Toast.LENGTH_SHORT).show();
        //mLoginViewModelMain.fetchUserDetails();
        Intent intent = MainActivity.newIntent(NameGenderActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void genderFailure(String strMessage) {
        Toast.makeText(getApplicationContext(), strMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void male() {
        mActivityNameGenderBinding.imgMale.setTextColor(getResources().getColor(R.color.eat_color));
        mActivityNameGenderBinding.imgFemale.setTextColor(getResources().getColor(R.color.dark_gray));
        mActivityNameGenderBinding.txtMale.setTextColor(getResources().getColor(R.color.eat_color));
        mActivityNameGenderBinding.txtFemale.setTextColor(getResources().getColor(R.color.dark_gray));
        gender = AppConstants.MALE;
    }

    @Override
    public void female() {
        mActivityNameGenderBinding.imgMale.setTextColor(getResources().getColor(R.color.dark_gray));
        mActivityNameGenderBinding.imgFemale.setTextColor(getResources().getColor(R.color.eat_color));
        mActivityNameGenderBinding.txtFemale.setTextColor(getResources().getColor(R.color.eat_color));
        mActivityNameGenderBinding.txtMale.setTextColor(getResources().getColor(R.color.dark_gray));
        gender = AppConstants.FEMALE;
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

        mActivityNameGenderBinding.imgMale.setTextColor(getResources().getColor(R.color.eat_color));
        mActivityNameGenderBinding.imgFemale.setTextColor(getResources().getColor(R.color.dark_gray));
        mActivityNameGenderBinding.txtMale.setTextColor(getResources().getColor(R.color.eat_color));
        mActivityNameGenderBinding.txtFemale.setTextColor(getResources().getColor(R.color.dark_gray));
        gender = 1;
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
