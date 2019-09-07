package com.tovo.eat.ui.cart.refund;

import android.app.Activity;
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

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityAddressListBinding;
import com.tovo.eat.databinding.ActivityRefundListBinding;
import com.tovo.eat.ui.address.add.AddAddressActivity;
import com.tovo.eat.ui.address.edit.EditAddressActivity;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.analytics.Analytics;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;

import javax.inject.Inject;

public class RefundListActivity extends BaseActivity<ActivityRefundListBinding, RefundListViewModel> implements RefundListNavigator, RefundListAdapter.LiveProductsAdapterListener {

    @Inject
    RefundListViewModel mRefundListViewModel;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    RefundListAdapter adapter;

    ActivityRefundListBinding mActivityRefundListBinding;

    Analytics analytics;
    String  pageName="Refund list";


    public static Intent newIntent(Context context) {

        return new Intent(context, RefundListActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityRefundListBinding = getViewDataBinding();
        mRefundListViewModel.setNavigator(this);
        adapter.setListener(this);


        analytics=new Analytics(this,pageName);

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityRefundListBinding.recyclerviewList.setLayoutManager(new LinearLayoutManager(this));
        mActivityRefundListBinding.recyclerviewList.setAdapter(adapter);


        mActivityRefundListBinding.refreshList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefundListViewModel.fetchRepos();
            }
        });


    }


    @Override
    public int getBindingVariable() {
        return BR.refundListViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_refund_list;
    }

    @Override
    public RefundListViewModel getViewModel() {
        return mRefundListViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void addNewAddress() {

        Intent intent = AddAddressActivity.newIntent(RefundListActivity.this);
        startActivity(intent);
    }

    @Override
    public void editAddress() {
        Intent intent = EditAddressActivity.newIntent(RefundListActivity.this);
        //  intent.putExtra("aid",)
        startActivity(intent);
    }

    @Override
    public void listLoaded() {
        mActivityRefundListBinding.refreshList.setRefreshing(false);

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
        mRefundListViewModel.fetchRepos();
    }

    @Override
    public void noAddress() {
       /* Intent intentAddress = AddAddressActivity.newIntent(RegionDetailsActivity.this);
        startActivity(intentAddress);
        finish();*/
        mActivityRefundListBinding.refreshList.setRefreshing(false);
    }


    private void subscribeToLiveData() {
        mRefundListViewModel.getRefundListItemsLiveData().observe(this,
                addrressListItemViewModel -> mRefundListViewModel.addDishItemsToList(addrressListItemViewModel));
    }


    @Override
    public void onResume() {
        super.onResume();
        mRefundListViewModel.fetchRepos();
        subscribeToLiveData();

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

        Intent intent=new Intent();
        setResult(Activity.RESULT_CANCELED,intent);
        finish();//finishing activity

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent=new Intent();
        setResult(Activity.RESULT_CANCELED,intent);
        finish();//finishing activity
    }

    @Override
    public void onItemClickData(RefundListResponse.Result result, int selected) {

        mRefundListViewModel.saveRefundId(result.getRcid());

        Intent intent=new Intent();
        intent.putExtra("refundid",result.getRcid());
        setResult(Activity.RESULT_OK,intent);
        finish();//finishing activity
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

