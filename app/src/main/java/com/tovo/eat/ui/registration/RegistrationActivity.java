package com.tovo.eat.ui.registration;


import android.Manifest;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.AccountPicker;
import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityRegistrationBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.home.kitchendish.KitchenDishActivity;
import com.tovo.eat.utilities.AppConstants;

import java.util.List;
import java.util.regex.Pattern;

import javax.inject.Inject;

public class RegistrationActivity extends BaseActivity<ActivityRegistrationBinding, RegistrationActivityViewModel>
        implements RegistrationActivityNavigator, AdapterView.OnItemSelectedListener {

    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%-+]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                    "(" +
                    "." +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                    ")+"
    );
    /* @Inject*/
    RegionAdapter mRegionAdapter;
    List<RegionResponse.Result> regionList;
    RegionResponse.Result selectRegionItem;
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
            String strEmail = mActivityRegistrationBinding.edtEmail.getText().toString();
            String strReTypePass = mActivityRegistrationBinding.edtReTypePassword.getText().toString();
            mRegistrationActivityViewModel.userRegistrationServiceCall(strEmail, strReTypePass);
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

        Intent intent = MainActivity.newIntent(RegistrationActivity.this);
        intent.putExtra("cart", true);
        startActivity(intent);
        finish();

    }

    @Override
    public void regFailure() {
        Toast.makeText(getApplicationContext(), "Registration failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void regionList(List<RegionResponse.Result> regionList) {
        this.regionList = regionList;
        mActivityRegistrationBinding.spnHometown.setAdapter(new RegionAdapter(this, regionList));
        //mActivityRegistrationBinding.spnHometown.setAdapter(mRegionAdapter);
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

    private void checkRunTimePermission() {
        String[] permissionArrays = new String[]{Manifest.permission.GET_ACCOUNTS};

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissionArrays, 11111);
        } else {
            requestEmailList();

            // if already permition granted
            // PUT YOUR ACTION (Like Open cemara etc..)
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean openActivityOnce = true;
        boolean openDialogOnce = true;
        boolean isPermitted = false;


        if (requestCode == 11111) {
            for (int i = 0; i < grantResults.length; i++) {
                String permission = permissions[i];

                isPermitted = grantResults[i] == PackageManager.PERMISSION_GRANTED;

                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    // user rejected the permission
                    boolean showRationale = false;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        showRationale = shouldShowRequestPermissionRationale(permission);
                    }
                    if (!showRationale) {
                        //execute when 'never Ask Again' tick and permission dialog not show
                    } else {
                        if (openDialogOnce) {
                          //  alertView();
                        }
                    }
                }
            }

            if (isPermitted)
              requestEmailList();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityRegistrationBinding = getViewDataBinding();
        mRegistrationActivityViewModel.setNavigator(this);
        subscribeToLiveData();

        //mRegionAdapter.setListener(this);
        /*mActivityRegistrationBinding.spnHometown.setAdapter(mRegionAdapter);*/

        mActivityRegistrationBinding.spnHometown.setOnItemSelectedListener(this);

      checkRunTimePermission();


    }

    private void subscribeToLiveData() {
        mRegistrationActivityViewModel.getRegions().observe(this,
                regionItemViewModel -> mRegistrationActivityViewModel.addRegionListItemsToList(regionItemViewModel));
    }

    @Override
    public void onBackPressed() {
        Intent intent = MainActivity.newIntent(RegistrationActivity.this);
        intent.putExtra("cart", true);
        startActivity(intent);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spn_hometown) {
            selectRegionItem = regionList.get(position);
            Log.e("sele", selectRegionItem.toString());
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void requestEmailList(){

        Intent googlePicker = AccountPicker.newChooseAccountIntent(null, null, new String[]{GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE}, true, null, null, null, null);
        startActivityForResult(googlePicker, 121);

    }
    protected void onActivityResult(final int requestCode, final int resultCode,
                                    final Intent data) {
        if (requestCode == 121 && resultCode == RESULT_OK) {
            String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);


            mActivityRegistrationBinding.edtEmail.setText(accountName);

        }
    }

}
