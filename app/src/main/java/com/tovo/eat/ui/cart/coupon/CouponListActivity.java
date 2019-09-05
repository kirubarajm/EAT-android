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
import com.tovo.eat.utilities.MvvmApp;
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


    boolean notClickable = false;


    public static Intent newIntent(Context context) {

        return new Intent(context, CouponListActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityCouponListBinding = getViewDataBinding();
        mCouponListViewModel.setNavigator(this);
        adapter.setListener(this);



        mActivityCouponListBinding.loader.setVisibility(View.VISIBLE);


        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            notClickable = intent.getExtras().getBoolean("clickable");
            mCouponListViewModel.notClickable.set( intent.getExtras().getBoolean("clickable"));
        }


        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityCouponListBinding.recyclerviewList.setLayoutManager(new LinearLayoutManager(this));
        mActivityCouponListBinding.recyclerviewList.setAdapter(adapter);


        mActivityCouponListBinding.refreshList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
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
        finish();
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
            mCouponListViewModel.saveCouponId(result.getCid(),result.getCouponName());


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

