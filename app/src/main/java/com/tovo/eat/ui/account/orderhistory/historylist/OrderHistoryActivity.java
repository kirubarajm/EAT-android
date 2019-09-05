package com.tovo.eat.ui.account.orderhistory.historylist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityOrdersHistoryListBinding;
import com.tovo.eat.ui.account.orderhistory.ordersview.OrderHistoryActivityView;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;

import javax.inject.Inject;

public class OrderHistoryActivity extends BaseActivity<ActivityOrdersHistoryListBinding, OrderHistoryActivityViewModel> implements OrderHistoryActivityNavigator,
        OrdersHistoryActivityAdapter.OrdersHistoryAdapterListener {

    @Inject
    OrderHistoryActivityViewModel mOrderHistoryActivityViewModel;
    ActivityOrdersHistoryListBinding mActivityOrdersHistoryListBinding;
    @Inject
    OrdersHistoryActivityAdapter mOrdersHistoryActivityAdapter;
    @Inject
    LinearLayoutManager mLayoutManager;
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

    public static Intent newIntent(Context context) {
        return new Intent(context, OrderHistoryActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void onRefreshLayout() {
        mOrderHistoryActivityViewModel.fetchRepos(1);
    }

    @Override
    public void onRefreshSuccess() {
        mActivityOrdersHistoryListBinding.swipeOrdersHistory.setRefreshing(false);
        mActivityOrdersHistoryListBinding.loader.setVisibility(View.GONE);
    }

    @Override
    public void onRefreshFailure() {
        mActivityOrdersHistoryListBinding.loader.setVisibility(View.GONE);
        mActivityOrdersHistoryListBinding.swipeOrdersHistory.setRefreshing(false);
    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public void goHome() {

        Intent intent = MainActivity.newIntent(OrderHistoryActivity.this);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }

    @Override
    public int getBindingVariable() {
        return BR.ordersHistoryViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_orders_history_list;
    }

    @Override
    public OrderHistoryActivityViewModel getViewModel() {
        return mOrderHistoryActivityViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityOrdersHistoryListBinding = getViewDataBinding();
        mOrderHistoryActivityViewModel.setNavigator(this);


        mActivityOrdersHistoryListBinding.loader.setVisibility(View.VISIBLE);



        mOrdersHistoryActivityAdapter.setListener(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityOrdersHistoryListBinding.recyclerviewOrders.setLayoutManager(new LinearLayoutManager(this));
        mActivityOrdersHistoryListBinding.recyclerviewOrders.setAdapter(mOrdersHistoryActivityAdapter);

        subscribeToLiveData();
    }

    private void subscribeToLiveData() {
        mOrderHistoryActivityViewModel.getOrders().observe(this,
                ordersItemViewModel -> mOrderHistoryActivityViewModel.addOrdersListItemsToList(ordersItemViewModel));
    }

    @Override
    public void listItem(OrdersHistoryListResponse.Result mOrderList) {

        Intent intent = OrderHistoryActivityView.newIntent(this);
        intent.putExtra("orderId",
                String.valueOf(mOrderList.getOrderid()));
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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


}
