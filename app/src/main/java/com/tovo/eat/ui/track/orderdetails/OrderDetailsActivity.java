package com.tovo.eat.ui.track.orderdetails;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityOrderDetailsBinding;
import com.tovo.eat.databinding.ActivityOrdersHistoryViewBinding;
import com.tovo.eat.ui.account.orderhistory.historylist.OrderHistoryActivity;
import com.tovo.eat.ui.account.orderhistory.historylist.OrdersHistoryActivityAdapter;
import com.tovo.eat.ui.account.orderhistory.historylist.OrdersHistoryListResponse;
import com.tovo.eat.ui.account.orderhistory.ordersview.OrdersHistoryActivityItemAdapter;
import com.tovo.eat.ui.account.orderhistory.ordersview.OrdersHistoryActivityResponse;
import com.tovo.eat.ui.alerts.ordercanceled.CancelListener;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.cart.BillListAdapter;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.analytics.Analytics;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;

import javax.inject.Inject;

public class OrderDetailsActivity extends BaseActivity<ActivityOrderDetailsBinding, OrderDetailsViewModel> implements OrderDetailsNavigator,
        OrdersHistoryActivityItemAdapter.OrdersHistoryAdapterListener, CancelListener {

    @Inject
    OrderDetailsViewModel mOrderDetailsViewModel;
    ActivityOrderDetailsBinding mActivityOrdersHostiryViewBinding;
    @Inject
    OrdersHistoryActivityItemAdapter mOrderDetailsAdapter;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    BillListAdapter billListAdapter;


    Analytics analytics;
    String  pageName= AppConstants.SCREEN_ORDER_DETAILS;


    public static Intent newIntent(Context context) {

        return new Intent(context, OrderDetailsActivity.class);
    }


    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void clearCart() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(OrderDetailsActivity.this);
        builder1.setMessage("Already you have added items in cart. Do you want to clear?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                       /* Intent intent= TestActivity.newIntent(OrderHelpActivity.this);
                        intent.putExtra("cart",true);
                        startActivity(intent);*/

                       mOrderDetailsViewModel.orderAvailable();

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
    public void orderRepeat() {
        Intent intent= MainActivity.newIntent(OrderDetailsActivity.this);
        intent.putExtra("cart",true);
        startActivity(intent);
    }

    @Override
    public void goBack() {
       onBackPressed();
    }

    @Override
    public void onBackPressed() {
        new Analytics().sendClickData(AppConstants.SCREEN_CURRENT_ORDER_DETAILS, AppConstants.CLICK_BACK_BUTTON);
        super.onBackPressed();
    }

    @Override
    public int getBindingVariable() {
        return BR.orderDetailsViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_details;
    }

    @Override
    public OrderDetailsViewModel getViewModel() {
        return mOrderDetailsViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityOrdersHostiryViewBinding = getViewDataBinding();
        mOrderDetailsViewModel.setNavigator(this);
        mOrderDetailsAdapter.setListener(this);



        analytics=new Analytics(this,pageName);

        mOrderDetailsAdapter.setListener(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityOrdersHostiryViewBinding.recyclerviewOrdersItems.setLayoutManager(new LinearLayoutManager(this));
        mActivityOrdersHostiryViewBinding.recyclerviewOrdersItems.setAdapter(mOrderDetailsAdapter);


        LinearLayoutManager billLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mActivityOrdersHostiryViewBinding.recyclerviewBill.setLayoutManager(billLayoutManager);
        mActivityOrdersHostiryViewBinding.recyclerviewBill.setAdapter(billListAdapter);

        subscribeToLiveData();
    }

    private void subscribeToLiveData() {
        mOrderDetailsViewModel.getOrders().observe(this,
                ordersItemViewModel -> mOrderDetailsViewModel.addOrdersListItemsToList(ordersItemViewModel));

        mOrderDetailsViewModel.getCartBillLiveData().observe(this,
                cartdetails -> mOrderDetailsViewModel.addBillItemsToList(cartdetails));
    }


    @Override
    public void listItem(OrdersHistoryActivityResponse.Result mOrderList) {

    }


    @Override
    protected void onResume() {
        super.onResume();
        registerWifiReceiver();


        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            // String strOrderId=bundle.getString("orderId");
            String strOrderId=getIntent().getExtras().getString("orderId");

            mOrderDetailsViewModel.fetchRepos(strOrderId);

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

    @Override
    public void canceled() {
        finish();
    }
}
