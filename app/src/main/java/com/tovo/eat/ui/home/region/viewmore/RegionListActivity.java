package com.tovo.eat.ui.home.region.viewmore;

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
import android.widget.Toast;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityRegionListBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.kitchendish.KitchenDishActivity;
import com.tovo.eat.ui.home.region.RegionsResponse;
import com.tovo.eat.ui.home.region.list.RegionDetailsActivity;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;

import javax.inject.Inject;

public class RegionListActivity extends BaseActivity<ActivityRegionListBinding, RegionListViewModel> implements RegionListNavigator, RegionsListAdapter.LiveProductsAdapterListener {

    @Inject
    RegionListViewModel mRegionListViewModel;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    RegionsListAdapter adapter;

    ActivityRegionListBinding mActivityRegionListBinding;

    public static Intent newIntent(Context context) {

        return new Intent(context, RegionListActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityRegionListBinding = getViewDataBinding();
        mRegionListViewModel.setNavigator(this);
        adapter.setListener(this);


        subscribeToLiveData();

       /* Intent intent = getIntent();
        if (intent.getExtras() != null) {
            mRegionListViewModel.fetchRepos(intent.getExtras().getInt("id"));

            subscribeToLiveData();
        }*/

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityRegionListBinding.recyclerviewList.setLayoutManager(new LinearLayoutManager(this));
        mActivityRegionListBinding.recyclerviewList.setAdapter(adapter);


        mActivityRegionListBinding.refreshList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRegionListViewModel.fetchRepos();
            }
        });


    }


    @Override
    public int getBindingVariable() {
        return BR.regionListViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_region_list;
    }

    @Override
    public RegionListViewModel getViewModel() {
        return mRegionListViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }


    @Override
    public void listLoaded() {
        mActivityRegionListBinding.refreshList.setRefreshing(false);

    }

    @Override
    public void goBack() {
        finish();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }



    private void subscribeToLiveData() {
        mRegionListViewModel.getRegionListItemsLiveData().observe(this,
               kitchensListItemViewModel -> mRegionListViewModel.addDishItemsToList(kitchensListItemViewModel));
    }


    @Override
    public void onResume() {
        super.onResume();


registerWifiReceiver();

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterWifiReceiver();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onItemClickData(RegionsResponse.Result mRegionList) {

        Intent intent = RegionDetailsActivity.newIntent(RegionListActivity.this);
        intent.putExtra("image", mRegionList.getRegionDetailImage());
        intent.putExtra("id", mRegionList.getRegionid());
        intent.putExtra("tagline", mRegionList.getTagline());
        startActivity(intent);
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

