package com.tovo.eat.ui.account.edit;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityAccEditBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.region.RegionSearchModel;
import com.tovo.eat.ui.signup.SignUpActivity;
import com.tovo.eat.ui.signup.namegender.RegionListAdapter;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.analytics.Analytics;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;

import java.util.List;

import javax.inject.Inject;

public class EditAccountActivity extends BaseActivity<ActivityAccEditBinding, EditAccountViewModel>
        implements EditAccountNavigator {

    @Inject
    EditAccountViewModel mLoginViewModelMain;
    int gender;
    int regionId = 0;
    RegionListAdapter regionListAdapter;
    RegionSearchModel.Result result;
    Analytics analytics;
    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (checkWifiConnect()) {
            } else {
                Intent inIntent = InternetErrorFragment.newIntent(MvvmApp.getInstance());
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(inIntent);
            }
        }
    };
    private ActivityAccEditBinding mActivityNameGenderBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, EditAccountActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void proceedClick() {
        String name = mActivityNameGenderBinding.edtName.getText().toString();
        String email = mActivityNameGenderBinding.edtEmail.getText().toString();

        if (validForProceed()) {
            mLoginViewModelMain. insertNameGenderServiceCall(name, email, regionId);

            new Analytics().sendClickData(AppConstants.SCREEN_EDIT_MYACCOUNT,AppConstants.CLICK_APPLY_CHANGES);

        }
    }

    @Override
    public void genderSuccess(String strMessage) {
        Toast.makeText(getApplicationContext(), strMessage, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void genderFailure(String strMessage) {
        Toast.makeText(getApplicationContext(), strMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void regionListLoaded(List<RegionSearchModel.Result> regionList) {

        regionListAdapter = new RegionListAdapter(this, android.R.layout.simple_dropdown_item_1line);
        mActivityNameGenderBinding.region.setThreshold(1);
        mActivityNameGenderBinding.region.setAdapter(regionListAdapter);
        regionListAdapter.setData(regionList);

    }

    @Override
    public void goBack() {
        new Analytics().sendClickData(AppConstants.SCREEN_EDIT_MYACCOUNT,AppConstants.CLICK_BACK_BUTTON);
        finish();
    }

    @Override
    public int getBindingVariable() {
        return BR.editAccountViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_acc_edit;
    }

    @Override
    public EditAccountViewModel getViewModel() {
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


         analytics=new Analytics(this, AppConstants.SCREEN_EDIT_MYACCOUNT);



        Intent intent = getIntent();
        if (intent.getExtras() != null) {

            String name = intent.getExtras().getString("name");
            String email = intent.getExtras().getString("email");
            String regionName = intent.getExtras().getString("region");
            gender = intent.getExtras().getInt("gender");
            regionId = intent.getExtras().getInt("regionid");

            if (gender == 1) {
                mLoginViewModelMain.male.set(true);
            } else {
                mLoginViewModelMain.male.set(false);

            }


            mActivityNameGenderBinding.edtName.setText(name);
            if (email != null)
                mActivityNameGenderBinding.edtEmail.setText(email);

            if (regionId==0) {
                mActivityNameGenderBinding.region.setText("");
                mActivityNameGenderBinding.region.setEnabled(false);
                mActivityNameGenderBinding.chkOthers.setChecked(true);
            } else {
                mActivityNameGenderBinding.region.setText(regionName);
                mActivityNameGenderBinding.region.setEnabled(true);
                mActivityNameGenderBinding.chkOthers.setChecked(false);
            }

        }


        mLoginViewModelMain.regionList();


        mActivityNameGenderBinding.region.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //get selected region name and id
                result = ((RegionListAdapter) mActivityNameGenderBinding.region.getAdapter()).getFilterList().get(position);
                regionId =result.getRegionid();

                mActivityNameGenderBinding.regionList.setErrorEnabled(false);

                new Analytics().regionSelected(result.getRegionname());

            }
        });


        mActivityNameGenderBinding.edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mActivityNameGenderBinding.inputName.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // other region
        mActivityNameGenderBinding.chkOthers.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    regionId = 0;
                    mActivityNameGenderBinding.region.setText("");
                    mActivityNameGenderBinding.region.setEnabled(false);
                    mActivityNameGenderBinding.chkOthers.setChecked(true);
                    new Analytics().sendClickData(AppConstants.SCREEN_EDIT_MYACCOUNT,AppConstants.CLICK_REGION_OTHER);

                } else {

                    mActivityNameGenderBinding.region.setText("");
                    mActivityNameGenderBinding.region.setEnabled(true);
                    mActivityNameGenderBinding.chkOthers.setChecked(false);
                }
            }
        });

        mActivityNameGenderBinding.region.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!mActivityNameGenderBinding.chkOthers.isChecked()) {
                    if (mActivityNameGenderBinding.region.getText().toString().length() == 0) {

                    }
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        new Analytics().sendClickData(AppConstants.SCREEN_EDIT_MYACCOUNT,AppConstants.CLICK_BACK_BUTTON);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = SignUpActivity.newIntent(EditAccountActivity.this);
        startActivity(intent);
        finish();
        return true;
    }

    private boolean validForProceed() {
        if (mActivityNameGenderBinding.edtName.getText().toString().equals("")) {
            if ((mActivityNameGenderBinding.edtName.getText().toString().equals(""))) {
                mActivityNameGenderBinding.inputName.setError("Enter your name");

            }
            return false;
        }

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerWifiReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterWifiReceiver();
    }

    private void registerWifiReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mWifiReceiver, filter);
    }

    private boolean checkWifiConnect() {
        ConnectivityManager manager = (ConnectivityManager) MvvmApp.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();


        ConnectivityManager cm =
                (ConnectivityManager) MvvmApp.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();


        if (networkInfo != null
                && networkInfo.getType() == ConnectivityManager.TYPE_WIFI
                && networkInfo.isConnected()) {
            return true;
        } else return networkInfo != null
                && networkInfo.isConnected();
    }

    private void unregisterWifiReceiver() {
        unregisterReceiver(mWifiReceiver);
    }


    @Override
    public void canceled() {

    }
}
