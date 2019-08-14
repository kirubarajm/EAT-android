package com.tovo.eat.ui.signup.namegender;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityNameGenderBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.home.region.RegionSearchModel;
import com.tovo.eat.ui.signup.SignUpActivity;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;

import java.util.List;

import javax.inject.Inject;

public class NameGenderActivity extends BaseActivity<ActivityNameGenderBinding, NameGenderActivityViewModel>
        implements NameGenderActivityNavigator {

    @Inject
    NameGenderActivityViewModel mLoginViewModelMain;
    int gender;
    int regionId = 0;
    private ActivityNameGenderBinding mActivityNameGenderBinding;


    RegionListAdapter regionListAdapter;


    RegionSearchModel.Result result;


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

        if (validForProceed())
            mLoginViewModelMain.insertNameGenderServiceCall(name,regionId,referral);
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

        regionListAdapter = new RegionListAdapter(this, android.R.layout.simple_dropdown_item_1line);
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

        mActivityNameGenderBinding.region.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                result = ((RegionListAdapter) mActivityNameGenderBinding.region.getAdapter()).getFilterList().get(position);
                //  Log.e("", selectedItem.getMenuitem_name());

                regionId = result.getRegionid();
                
                mActivityNameGenderBinding.regionList.setErrorEnabled(false);

                if (regionId == 0){
                    mLoginViewModelMain.flagRegion.set(true);
                }else {
                    mLoginViewModelMain.flagRegion.set(false);
                }


                mActivityNameGenderBinding.regionList.setErrorEnabled(false);
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
        if (mActivityNameGenderBinding.edtName.getText().toString().equals("") || regionId == 0) {


            if ((mActivityNameGenderBinding.edtName.getText().toString().equals(""))) {
                mActivityNameGenderBinding.inputName.setError("Enter your name");

            }

            if (regionId == 0) {
                mActivityNameGenderBinding.regionList.setError("Enter your region");
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


    private  boolean checkWifiConnect() {
        ConnectivityManager manager = (ConnectivityManager) MvvmApp.getInstance(). getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();


        ConnectivityManager cm =
                (ConnectivityManager) MvvmApp.getInstance() .getSystemService(Context.CONNECTIVITY_SERVICE);

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

    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //   if (mMainViewModel.isAddressAdded()) {
            if (checkWifiConnect()) {
            } else {
                Intent inIntent= InternetErrorFragment.newIntent(MvvmApp.getInstance());
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
    private  void unregisterWifiReceiver() {
        unregisterReceiver(mWifiReceiver);
    }


}
