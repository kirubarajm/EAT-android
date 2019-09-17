package com.tovo.eat.ui.address.select;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityAddressSelectBinding;
import com.tovo.eat.ui.address.add.AddAddressActivity;
import com.tovo.eat.ui.address.edit.EditAddressActivity;
import com.tovo.eat.ui.address.list.AddressListActivity;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.analytics.Analytics;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;

import javax.inject.Inject;

public class SelectAddressListActivity extends BaseActivity<ActivityAddressSelectBinding, SelectAddressListViewModel> implements SelectAddressListNavigator, SelectAddressListAdapter.LiveProductsAdapterListener {

    @Inject
    SelectAddressListViewModel mSelectAddressListViewModel;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    SelectAddressListAdapter adapter;

    ActivityAddressSelectBinding mActivityAddressSelectBinding;

    Analytics analytics;
    String  pageName= AppConstants.CLICK_MANAGE_ADDRESS;


    public static Intent newIntent(Context context) {

        return new Intent(context, SelectAddressListActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityAddressSelectBinding = getViewDataBinding();
        mSelectAddressListViewModel.setNavigator(this);
        adapter.setListener(this);



        analytics=new Analytics(this,pageName);


        mActivityAddressSelectBinding.loader.setVisibility(View.VISIBLE);


        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityAddressSelectBinding.recyclerviewList.setLayoutManager(new LinearLayoutManager(this));
        mActivityAddressSelectBinding.recyclerviewList.setAdapter(adapter);
        subscribeToLiveData();


        mActivityAddressSelectBinding.refreshList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Analytics().sendClickData(pageName,AppConstants.CLICK_REFRESH);
                mSelectAddressListViewModel.fetchRepos();
            }
        });


    }


    @Override
    public int getBindingVariable() {
        return BR.selectAddressListViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_address_select;
    }

    @Override
    public SelectAddressListViewModel getViewModel() {
        return mSelectAddressListViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void addNewAddress() {
        new Analytics().sendClickData(pageName,AppConstants.CLICK_ADD_NEW_ADDRESS);
        Intent intent = AddAddressActivity.newIntent(SelectAddressListActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void editAddress() {
        Intent intent = EditAddressActivity.newIntent(SelectAddressListActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void listLoaded() {
        mActivityAddressSelectBinding.refreshList.setRefreshing(false);

        mActivityAddressSelectBinding.loader.setVisibility(View.GONE);
    }

    @Override
    public void goBack() {
        new Analytics().sendClickData(pageName,AppConstants.CLICK_BACK_BUTTON);
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();//finishing activity
    }


    private void subscribeToLiveData() {
        mSelectAddressListViewModel.getSelectAddrressListItemsLiveData().observe(this,
                addrressListItemViewModel -> mSelectAddressListViewModel.addDishItemsToList(addrressListItemViewModel));
    }


    @Override
    public void onBackPressed() {
        new Analytics().sendClickData(pageName,AppConstants.CLICK_BACK_BUTTON);
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();//finishing activity
        super.onBackPressed();
    }

    @Override
    public void onResume() {
        super.onResume();
        //  mSelectAddressListViewModel.fetchRepos();
        registerWifiReceiver();
    }


    @Override
    protected void onPause() {
        super.onPause();
        unregisterWifiReceiver();
    }

    @Override
    public void onItemClickData(SelectAddressListResponse.Result address) {
        new Analytics().sendClickData(pageName,AppConstants.CLICK_SELECT_ADDRESS);
        mSelectAddressListViewModel.updateCurrentAddress(address.getAddressTitle(), address.getAddress(), address.getLat(), address.getLon(),address.getLocality(),address.getAid());
        Intent intent = new Intent();
        setResult(Activity.RESULT_OK, intent);
        finish();//finishing activity
    }

    @Override
    public void editAddressClick(SelectAddressListResponse.Result address) {
        new Analytics().sendClickData(pageName,AppConstants.CLICK_EDIT);
        Intent intent = EditAddressActivity.newIntent(SelectAddressListActivity.this);
        intent.putExtra("aid",address.getAid());
        intent.putExtra("type", address.getAddressType());
        startActivity(intent);
        finish();

    }

    @Override
    protected void onDestroy() {
       /* Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);*/
        super.onDestroy();
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

