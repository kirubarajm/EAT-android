package com.tovo.eat.ui.address.list;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityAddressListBinding;
import com.tovo.eat.ui.address.add.AddAddressActivity;
import com.tovo.eat.ui.address.edit.EditAddressActivity;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.utilities.MvvmApp;
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

    public static Intent newIntent(Context context) {

        return new Intent(context, AddressListActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityAddressListBinding = getViewDataBinding();
        mAddressListViewModel.setNavigator(this);
        adapter.setListener(this);


      /*  Intent intent = getIntent();
        if (intent.getExtras() != null) {
            if (intent.getExtras().getString("for").equals("new")) {
                if (!mAddressListViewModel.haveAddress) {
                    Intent intentAddress = PaymentActivity.newIntent(RegionDetailsActivity.this);
                    startActivity(intentAddress);
                    finish();
                }
            }
        }*/

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

    }

    @Override
    public void goBack() {
        finish();
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
       /* Intent intentAddress = AddAddressActivity.newIntent(RegionDetailsActivity.this);
        startActivity(intentAddress);
        finish();*/
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

        mAddressListViewModel.setCurrentAddress(blogUrl);
        finish();
    }

    @Override
    public void editAddressClick(AddressListResponse.Result address) {
        Intent intent = EditAddressActivity.newIntent(AddressListActivity.this);
        intent.putExtra("aid", address.getAid());
        startActivity(intent);

    }

    @Override
    public void deleteAddress(AddressListResponse.Result addressList) {


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

                        if (addressList.getAddressType().equals("1")){
                            mAddressListViewModel.getDataManager().setHomeAddressAdded(false);
                        }else
                        if (addressList.getAddressType().equals("2")){
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
        super.onBackPressed();
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

