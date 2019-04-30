package com.tovo.eat.ui.registration;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityRegistrationBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.utilities.AppConstants;

import java.util.List;
import java.util.regex.Pattern;

import javax.inject.Inject;

public class RegistrationActivity extends BaseActivity<ActivityRegistrationBinding, RegistrationActivityViewModel>
        implements RegistrationActivityNavigator {

    @Inject
    RegionAdapter mRegionAdapter;

    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%-+]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                    "(" +
                    "." +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                    ")+"
    );
    @Inject
    RegistrationActivityViewModel mRegistrationActivityViewModel;
    private ActivityRegistrationBinding mActivityRegistrationBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, RegistrationActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void usersRegistrationMain() {
        if (validForProceed()) {
            String strEmail=mActivityRegistrationBinding.edtEmail.getText().toString();
            String strReTypePass=mActivityRegistrationBinding.edtReTypePassword.getText().toString();
            mRegistrationActivityViewModel.userRegistrationServiceCall(strEmail,strReTypePass);
        }
    }

    @Override
    public void openMainActivity() {
        Intent intent = MainActivity.newIntent(RegistrationActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void regSuccess(String strSucess) {
        mActivityRegistrationBinding.edtEmail.setText("");
        mActivityRegistrationBinding.edtPassword.setText("");
        mActivityRegistrationBinding.edtReTypePassword.setText("");
        Toast.makeText(getApplicationContext(), strSucess, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void regFailure() {
        Toast.makeText(getApplicationContext(), "Registration failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void regionList(List<RegionResponse.Result> regionList) {
        //mActivityRegistrationBinding.spnHometown.setAdapter(new RegionAdapter(RegistrationActivity.this, regionList));
    }

    @Override
    public int getBindingVariable() {
        return BR.registrationViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_registration;
    }

    @Override
    public RegistrationActivityViewModel getViewModel() {
        return mRegistrationActivityViewModel;
    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityRegistrationBinding = getViewDataBinding();
        mRegistrationActivityViewModel.setNavigator(this);
        subscribeToLiveData();


         /*mRegionAdapter.setListener(this);*/
         mActivityRegistrationBinding.spnHometown.setAdapter(mRegionAdapter);


    }

    private void subscribeToLiveData() {
        mRegistrationActivityViewModel.getRegions().observe(this,
                regionItemViewModel -> mRegistrationActivityViewModel.addRegionListItemsToList(regionItemViewModel));
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    private boolean validForProceed() {
        String cpass = mActivityRegistrationBinding.edtReTypePassword.getText().toString();
        String pass = mActivityRegistrationBinding.edtPassword.getText().toString();

        if (mActivityRegistrationBinding.edtEmail.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), AppConstants.TOAST_ENTER_EMAIL, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!EMAIL_ADDRESS_PATTERN.matcher(mActivityRegistrationBinding.edtEmail.getText().toString()).matches()) {
            Toast.makeText(getApplicationContext(), AppConstants.TOAST_ENTER_INVALID_EMAIL, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mActivityRegistrationBinding.edtPassword.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), AppConstants.TOAST_ENTER_PASSWORD, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mActivityRegistrationBinding.edtReTypePassword.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), AppConstants.TOAST_ENTER_RE_ENTER_PASSWORD, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!pass.equals(cpass)) {
            Toast.makeText(getApplicationContext(), AppConstants.TOAST_PASSWORD_NOT_MATCHING, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
