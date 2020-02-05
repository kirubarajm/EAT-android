package com.tovo.eat.ui.signup.namegender;


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
import com.tovo.eat.databinding.ActivityNameGenderBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.home.region.RegionSearchModel;
import com.tovo.eat.ui.signup.SignUpActivity;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.analytics.Analytics;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;

import java.util.List;

import javax.inject.Inject;

public class NameGenderActivity extends BaseActivity<ActivityNameGenderBinding, NameGenderActivityViewModel>
        implements NameGenderActivityNavigator {

    @Inject
    NameGenderActivityViewModel mLoginViewModelMain;
    int gender;
    String regionId = "";
    RegionListAdapter regionListAdapter;
    RegionSearchModel.Result result;


    Analytics analytics;
    String  pageName= AppConstants.SCREEN_USER_REGISTRATION;
    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //   if (mMainViewModel.isAddressAdded()) {
            if (checkWifiConnect()) {
            } else {
                Intent inIntent = InternetErrorFragment.newIntent(MvvmApp.getInstance());
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(inIntent);
               /* FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                InternetErrorFragment fragment = new InternetErrorFragment();
                transaction.replace(R.id.content_main, fragment);
                transaction.commit();
                internetCheck = true;*/
            }
        }
    };
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
        String referral = mActivityNameGenderBinding.referral.getText().toString();
        String otherregion = mActivityNameGenderBinding.otherRegionname.getText().toString();

        if (validForProceed()) {
            mLoginViewModelMain.insertNameGenderServiceCall(name, Integer.parseInt(regionId), referral,otherregion);
            new Analytics().sendClickData(AppConstants.SCREEN_USER_REGISTRATION, AppConstants.CLICK_PROCEED);
        }
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
    public void regionListLoaded(List<RegionSearchModel.Result> regionList) {

        regionListAdapter = new RegionListAdapter(this, android.R.layout.simple_list_item_1);
        mActivityNameGenderBinding.region.setThreshold(1);
        mActivityNameGenderBinding.region.setAdapter(regionListAdapter);
        regionListAdapter.setData(regionList);

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

        mLoginViewModelMain.regionList();

        analytics=new Analytics(this, pageName);

        mActivityNameGenderBinding.region.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                result = ((RegionListAdapter) mActivityNameGenderBinding.region.getAdapter()).getFilterList().get(position);

                regionId = String.valueOf(result.getHometownid());

                mActivityNameGenderBinding.regionList.setErrorEnabled(false);

                hideKeyboard();
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
                        regionId = "";
                    }
                }
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

        mActivityNameGenderBinding.chkOthers.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    regionId = "0";
                    mActivityNameGenderBinding.region.setText("");
                    mActivityNameGenderBinding.region.setEnabled(false);
                    mLoginViewModelMain.regionotherClicked.set(true);
                } else {
                    regionId = "";
                    mActivityNameGenderBinding.region.setEnabled(true);
                    mLoginViewModelMain.regionotherClicked.set(false);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
       new Analytics().sendClickData(AppConstants.SCREEN_USER_REGISTRATION,AppConstants.CLICK_BACK_BUTTON);
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = SignUpActivity.newIntent(NameGenderActivity.this);
        startActivity(intent);
        finish();
        return true;
    }

    private boolean validForProceed() {
        if (mActivityNameGenderBinding.edtName.getText().toString().equals("") || regionId.equals("")) {


            if ((mActivityNameGenderBinding.edtName.getText().toString().equals(""))) {
                mActivityNameGenderBinding.inputName.setError("Enter your name");

            }

            if (regionId.equals("")) {
                mActivityNameGenderBinding.regionList.setError("Enter your hometown");
            }

            // Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
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
