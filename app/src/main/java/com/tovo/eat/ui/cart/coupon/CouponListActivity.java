package com.tovo.eat.ui.cart.coupon;

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
import android.widget.Toast;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityCouponListBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.analytics.Analytics;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;

import javax.inject.Inject;

public class CouponListActivity extends BaseActivity<ActivityCouponListBinding, CouponListViewModel> implements CouponListNavigator, CouponListAdapter.LiveProductsAdapterListener {

    @Inject
    CouponListViewModel mCouponListViewModel;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    CouponListAdapter adapter;

    ActivityCouponListBinding mActivityCouponListBinding;
    Analytics analytics;
    String pageName = AppConstants.SCREEN_COUPON_LIST;

    boolean notClickable = false;
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

        return new Intent(context, CouponListActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityCouponListBinding = getViewDataBinding();
        mCouponListViewModel.setNavigator(this);
        adapter.setListener(this);


        analytics = new Analytics(this, pageName);

        mActivityCouponListBinding.loader.setVisibility(View.VISIBLE);


        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            notClickable = intent.getExtras().getBoolean("clickable");
            mCouponListViewModel.notClickable.set(intent.getExtras().getBoolean("clickable"));
        }


        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityCouponListBinding.recyclerviewList.setLayoutManager(new LinearLayoutManager(this));
        mActivityCouponListBinding.recyclerviewList.setAdapter(adapter);


        mActivityCouponListBinding.refreshList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Analytics().sendClickData(pageName, AppConstants.CLICK_REFRESH);

                mCouponListViewModel.fetchRepos();
            }
        });


    }

    @Override
    public int getBindingVariable() {
        return BR.couponListViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_coupon_list;
    }

    @Override
    public CouponListViewModel getViewModel() {
        return mCouponListViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void listLoaded() {
        mActivityCouponListBinding.refreshList.setRefreshing(false);


        mActivityCouponListBinding.loader.setVisibility(View.GONE);

    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public void noList() {

    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void couponValid(Integer cid) {
        new Analytics().sendClickData(pageName, AppConstants.CLICK_APPLY);
        Intent intent = new Intent();
        intent.putExtra("couponid", cid);
        setResult(Activity.RESULT_OK, intent);
        finish();//finishing activity

    }

    private void subscribeToLiveData() {
        mCouponListViewModel.getcouponListItemsLiveData().observe(this,
                couponsListItemViewModel -> mCouponListViewModel.addDishItemsToList(couponsListItemViewModel));
    }

    @Override
    public void onResume() {
        super.onResume();
        registerWifiReceiver();
        mCouponListViewModel.fetchRepos();
        subscribeToLiveData();

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterWifiReceiver();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new Analytics().sendClickData(pageName, AppConstants.CLICK_BACK_BUTTON);
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();//finishing activity

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();//finishing activity
    }

    @Override
    public void onItemClickData(CouponListResponse.Result result, int selected) {
        if (!notClickable) {
            mCouponListViewModel.saveCouponId(result.getCid(), result.getCouponName());
            new Analytics().sendClickData(pageName, AppConstants.CLICK_SELECT);
            Intent intent = new Intent();
            intent.putExtra("couponid", result.getCid());
            setResult(Activity.RESULT_OK, intent);
            finish();//finishing activity
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

