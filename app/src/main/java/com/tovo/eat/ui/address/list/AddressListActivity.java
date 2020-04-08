package com.tovo.eat.ui.address.list;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityAddressListBinding;
import com.tovo.eat.ui.address.add.AddAddressActivity;
import com.tovo.eat.ui.address.edit.EditAddressActivity;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.GpsUtils;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.analytics.Analytics;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;

import javax.inject.Inject;

public class AddressListActivity extends BaseActivity<ActivityAddressListBinding, AddressListViewModel> implements AddressListNavigator, AddressListAdapter.LiveProductsAdapterListener {

    @Inject
    AddressListViewModel mAddressListViewModel;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    AddressListAdapter adapter;

    ActivityAddressListBinding mActivityAddressListBinding;
    Analytics analytics;
    String pageName = AppConstants.CLICK_MANAGE_ADDRESS;
    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!checkWifiConnect()) {
                Intent inIntent = InternetErrorFragment.newIntent(MvvmApp.getInstance());
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(inIntent);
            }
        }
    };

    public static Intent newIntent(Context context) {

        return new Intent(context, AddressListActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityAddressListBinding = getViewDataBinding();
        mAddressListViewModel.setNavigator(this);
        adapter.setListener(this);

        analytics = new Analytics(this, pageName);
        mActivityAddressListBinding.loader.setVisibility(View.VISIBLE);


        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityAddressListBinding.recyclerviewList.setLayoutManager(new LinearLayoutManager(this));
        mActivityAddressListBinding.recyclerviewList.setAdapter(adapter);


        mActivityAddressListBinding.refreshList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAddressListViewModel.fetchRepos();
            }
        });


    }

    @Override
    public int getBindingVariable() {
        return BR.addressListViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_address_list;
    }

    @Override
    public AddressListViewModel getViewModel() {
        return mAddressListViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void addNewAddress() {
        new Analytics().sendClickData(pageName, AppConstants.CLICK_ADD_NEW_ADDRESS);

        Intent intent = AddAddressActivity.newIntent(AddressListActivity.this);
        startActivity(intent);
    }

    @Override
    public void editAddress() {

        Intent intent = EditAddressActivity.newIntent(AddressListActivity.this);
        //  intent.putExtra("aid",)
        startActivity(intent);
    }

    @Override
    public void listLoaded() {
        mActivityAddressListBinding.refreshList.setRefreshing(false);
        mActivityAddressListBinding.loader.setVisibility(View.GONE);
    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addresDeleted() {
        mAddressListViewModel.fetchRepos();
    }

    @Override
    public void noAddress() {
        mActivityAddressListBinding.refreshList.setRefreshing(false);
    }

    private void subscribeToLiveData() {
        mAddressListViewModel.getAddrressListItemsLiveData().observe(this,
                addrressListItemViewModel -> mAddressListViewModel.addDishItemsToList(addrressListItemViewModel));
    }

    @Override
    public void onResume() {
        super.onResume();
        mAddressListViewModel.fetchRepos();
        subscribeToLiveData();

        registerWifiReceiver();


    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterWifiReceiver();
    }

    @Override
    public void onItemClickData(AddressListResponse.Result blogUrl) {
    }

    @Override
    public void editAddressClick(AddressListResponse.Result address) {
        new Analytics().sendClickData(pageName, AppConstants.CLICK_EDIT);
        Intent intent = EditAddressActivity.newIntent(AddressListActivity.this);
        intent.putExtra("aid", address.getAid());
        intent.putExtra("type", address.getAddressType());
        startActivity(intent);

    }

    @Override
    public void deleteAddress(AddressListResponse.Result addressList) {

        new Analytics().sendClickData(pageName, AppConstants.CLICK_DELETE);


        AlertDialog.Builder builder1 = new AlertDialog.Builder(AddressListActivity.this);
        builder1.setMessage("Are you sure want to delete?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();


                        mAddressListViewModel.deleteAddress(addressList.getAid());
                        //   mAddressListViewModel.fetchRepos();

                        if (addressList.getAddressType().equals("1")) {
                            mAddressListViewModel.getDataManager().setHomeAddressAdded(false);
                        } else if (addressList.getAddressType().equals("2")) {
                            mAddressListViewModel.getDataManager().setOfficeAddressAdded(false);
                        }

                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();


    }

    @Override
    public void onBackPressed() {
        new Analytics().sendClickData(pageName, AppConstants.CLICK_BACK_BUTTON);
        super.onBackPressed();
    }

    public void turnOnGps() {

        new GpsUtils(this,AppConstants.GPS_REQUEST).turnGPSOn(new GpsUtils.onGpsListener() {
            @Override
            public void gpsStatus(boolean isGPSEnable) {
                // turn on GPS
                if (isGPSEnable) {
                    if (ActivityCompat.checkSelfPermission(AddressListActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AddressListActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(AddressListActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, AppConstants.GPS_REQUEST);

                    } else {
                        Intent intent = AddAddressActivity.newIntent(AddressListActivity.this);
                        startActivity(intent);
                    }
                } else {
                    turnOnGps();
                }


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == AppConstants.GPS_REQUEST) {

            if (resultCode == Activity.RESULT_OK) {
                turnOnGps();
            }

        }
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

