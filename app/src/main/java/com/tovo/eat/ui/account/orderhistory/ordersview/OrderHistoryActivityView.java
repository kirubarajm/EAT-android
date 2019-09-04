package com.tovo.eat.ui.account.orderhistory.ordersview;
import android.app.Dialog;
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
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityOrdersHistoryViewBinding;
import com.tovo.eat.ui.address.list.AddressListActivity;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.cart.BillListAdapter;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.home.kitchendish.KitchenDishActivity;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.fonts.poppins.ButtonTextView;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class OrderHistoryActivityView extends BaseActivity<ActivityOrdersHistoryViewBinding,OrderHistoryActivityViewModelView> implements OrderHistoryActivityViewNavigator,
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
    BillListAdapter billListAdapter;
    Dialog dialog;

    public static Intent newIntent(Context context) {

        return new Intent(context, OrderHistoryActivityView.class);
    }


    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void clearCart() {




showDialog();





/*
        AlertDialog.Builder builder1 = new AlertDialog.Builder(OrderHistoryActivityView.this);
        builder1.setMessage("Already you have added items in cart. Do you want to clear?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                       *//* Intent intent= TestActivity.newIntent(OrderHelpActivity.this);
                        intent.putExtra("cart",true);
                        startActivity(intent);*//*

                       mOrderHistoryActivityViewModelView.orderAvailable();

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
        alert11.show();*/



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
        Intent intent= MainActivity.newIntent(OrderHistoryActivityView.this);
        intent.putExtra("cart",true);
        startActivity(intent);
    }

    @Override
    public void goBack() {
        onBackPressed();
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

        dialog=new Dialog(this);



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
        if (bundle!=null){
            // String strOrderId=bundle.getString("orderId");
            String strOrderId=getIntent().getExtras().getString("orderId");

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
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
