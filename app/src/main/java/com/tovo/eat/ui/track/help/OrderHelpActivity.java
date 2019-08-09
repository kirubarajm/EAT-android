package com.tovo.eat.ui.track.help;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityOrderHelpBinding;
import com.tovo.eat.ui.account.feedbackandsupport.support.SupportActivity;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;

import javax.inject.Inject;

public class OrderHelpActivity extends BaseActivity<ActivityOrderHelpBinding, OrderHelpViewModel> implements OrderHelpNavigator, View.OnTouchListener {

    @Inject
    OrderHelpViewModel mOrderHelpViewModel;
    ActivityOrderHelpBinding mActivityOrderHelpBinding;
    String strOrderId;

    public static Intent newIntent(Context context) {

        return new Intent(context, OrderHelpActivity.class);
    }


    @Override
    public void handleError(Throwable throwable) {

    }


    @Override
    public void goBack() {
        finish();
    }

    @Override
    public void callDelivery() {

      /*  Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + Uri.encode(mOrderHelpViewModel.deliveryNumber.get().trim())));
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(callIntent);*/

        if (ContextCompat.checkSelfPermission(OrderHelpActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(OrderHelpActivity.this, new String[]{Manifest.permission.CALL_PHONE}, AppConstants.CALL_PHONE_PERMISSION_REQUEST_CODE);
        }else{
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + Uri.encode(mOrderHelpViewModel.deliveryNumber.get().trim())));
            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(callIntent);
        }
    }

    @Override
    public void gotoSupport() {

        Intent intent = SupportActivity.newIntent(OrderHelpActivity.this);
        startActivity(intent);


    }

    @Override
    public void orderCanceled() {

        Intent intent = MainActivity.newIntent(OrderHelpActivity.this);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }

    @Override
    public void orderCancelFailed() {
        finish();
        Toast.makeText(this, "Sorry! Cannot cancel this order", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getBindingVariable() {
        return BR.orderHelpViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_help;
    }

    @Override
    public OrderHelpViewModel getViewModel() {
        return mOrderHelpViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityOrderHelpBinding = getViewDataBinding();
        mOrderHelpViewModel.setNavigator(this);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mOrderHelpViewModel.deliveryName.set(getIntent().getExtras().getString("name"));
            mOrderHelpViewModel.deliveryNumber.set(getIntent().getExtras().getString("number"));
            mOrderHelpViewModel.deliveryAssigned.set(getIntent().getExtras().getBoolean("status"));
        }

        mActivityOrderHelpBinding.cancelReason1.setOnTouchListener(this);
        mActivityOrderHelpBinding.cancelReason2.setOnTouchListener(this);
        mActivityOrderHelpBinding.cancelReason3.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        int id = v.getId();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                v.setBackgroundColor(getResources().getColor(R.color.light_eat_color));
                break;
            case MotionEvent.ACTION_UP:
                v.setBackgroundColor(getResources().getColor(R.color.gray));
                //set color back to default

                showAlert(id);
                /*
                switch (id) {
                    case R.id.cancel_reason1:
                        //    mOrderHelpViewModel.cancelOrder1();
                        break;
                    case R.id.cancel_reason2:
                        //  mOrderHelpViewModel.cancelOrder2();
                        break;
                    case R.id.cancel_reason3:
                        //  mOrderHelpViewModel.cancelOrder3();
                        break;

                }*/
                break;
        }
        return true;
    }


    public void showAlert(int id) {


        AlertDialog alertDialog = new AlertDialog.Builder(OrderHelpActivity.this).create();
        alertDialog.setTitle("Are you sure want cancel this order?");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (id) {
                            case R.id.cancel_reason1:
                                    mOrderHelpViewModel.cancelOrder1();
                                break;
                            case R.id.cancel_reason2:
                                 mOrderHelpViewModel.cancelOrder2();
                                break;
                            case R.id.cancel_reason3:
                                  mOrderHelpViewModel.cancelOrder3();
                                break;
                        }
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case AppConstants.CALL_PHONE_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + Uri.encode(mOrderHelpViewModel.deliveryNumber.get().trim())));
                    callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(callIntent);
                } else{
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + Uri.encode(mOrderHelpViewModel.deliveryNumber.get().trim())));
                    callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(callIntent);
                }
                return;
            }
        }
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

