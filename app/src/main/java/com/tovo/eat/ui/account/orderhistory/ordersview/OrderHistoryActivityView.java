package com.tovo.eat.ui.account.orderhistory.ordersview;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityOrdersHistoryViewBinding;
import com.tovo.eat.ui.account.chatsupport.HistoryHelpActivity;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.analytics.Analytics;
import com.tovo.eat.utilities.fonts.poppins.ButtonTextView;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;
import com.zopim.android.sdk.api.ChatApi;
import com.zopim.android.sdk.api.ZopimChatApi;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class OrderHistoryActivityView extends BaseActivity<ActivityOrdersHistoryViewBinding, OrderHistoryActivityViewModelView> implements OrderHistoryActivityViewNavigator,
        OrdersHistoryActivityItemAdapter.OrdersHistoryAdapterListener, HasSupportFragmentInjector {

    @Inject
    OrderHistoryActivityViewModelView mOrderHistoryActivityViewModelView;
    ActivityOrdersHistoryViewBinding mActivityOrdersHostiryViewBinding;
    @Inject
    OrdersHistoryActivityItemAdapter mOrdersHistoryActivityItemAdapter;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    OrderBillListAdapter billListAdapter;
    Dialog dialog;
    Analytics analytics;
    String pageName = AppConstants.SCREEN_ORDER_DETAILS;

    String strOrderId;
    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //   if (mMainViewModel.isAddressAdded()) {
            if (!checkWifiConnect()) {
                Intent inIntent = InternetErrorFragment.newIntent(MvvmApp.getInstance());
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(inIntent);
            }
        }
    };

    public static Intent newIntent(Context context) {

        return new Intent(context, OrderHistoryActivityView.class);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void clearCart() {
        showDialog();
    }

    public void showDialog() {
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_repeat_order);

        ButtonTextView text = dialog.findViewById(R.id.add);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                mOrderHistoryActivityViewModelView.orderAvailable();


            }
        });

        ButtonTextView dialogButton = dialog.findViewById(R.id.cancel);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    @Override
    public void orderRepeat() {


        new Analytics().sendClickData(pageName, AppConstants.CLICK_REPEAT_THIS_ORDER);


        new Analytics().repeatOrder(strOrderId);
        Intent intent = MainActivity.newIntent(OrderHistoryActivityView.this);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("cart", true);
        startActivity(intent);
    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public void gotoSupport() {
       /* Intent intent = SupportActivity.newIntent(OrderHistoryActivityView.this);
        intent.putExtra("orderid",Long.parseLong(strOrderId));
        intent.putExtra("type",AppConstants.QUERY_TYPE_ORDER_HISTORY);
        startActivity(intent);*/


        Intent intent = HistoryHelpActivity.newIntent(this);
        intent.putExtra("orderid", Long.parseLong(strOrderId));
        startActivity(intent);


    }

    @Override
    public void onBackPressed() {
        new Analytics().sendClickData(pageName, AppConstants.CLICK_BACK_BUTTON);
        super.onBackPressed();
    }

    @Override
    public int getBindingVariable() {
        return BR.OrdersHistryViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_orders_history_view;
    }

    @Override
    public OrderHistoryActivityViewModelView getViewModel() {
        return mOrderHistoryActivityViewModelView;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityOrdersHostiryViewBinding = getViewDataBinding();
        mOrderHistoryActivityViewModelView.setNavigator(this);
        mOrdersHistoryActivityItemAdapter.setListener(this);


        analytics = new Analytics(this, pageName);

        dialog = new Dialog(this);






        mOrdersHistoryActivityItemAdapter.setListener(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityOrdersHostiryViewBinding.recyclerviewOrdersItems.setLayoutManager(new LinearLayoutManager(this));
        mActivityOrdersHostiryViewBinding.recyclerviewOrdersItems.setAdapter(mOrdersHistoryActivityItemAdapter);


        LinearLayoutManager billLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mActivityOrdersHostiryViewBinding.recyclerviewBill.setLayoutManager(billLayoutManager);
        mActivityOrdersHostiryViewBinding.recyclerviewBill.setAdapter(billListAdapter);


        subscribeToLiveData();
    }

    private void subscribeToLiveData() {
        mOrderHistoryActivityViewModelView.getOrders().observe(this,
                ordersItemViewModel -> mOrderHistoryActivityViewModelView.addOrdersListItemsToList(ordersItemViewModel));

        mOrderHistoryActivityViewModelView.getCartBillLiveData().observe(this,
                cartdetails -> mOrderHistoryActivityViewModelView.addBillItemsToList(cartdetails));

    }

    @Override
    public void listItem(OrdersHistoryActivityResponse.Result mOrderList) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerWifiReceiver();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            // String strOrderId=bundle.getString("orderId");
            strOrderId = getIntent().getExtras().getString("orderId");

            mOrderHistoryActivityViewModelView.fetchRepos(strOrderId);

        }

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
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public void canceled() {

    }
}
