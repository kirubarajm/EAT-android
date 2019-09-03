package com.tovo.eat.ui.track.help;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityOrderHelpBinding;
import com.tovo.eat.ui.account.feedbackandsupport.support.SupportActivity;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;

import javax.inject.Inject;

public class OrderHelpActivity extends BaseActivity<ActivityOrderHelpBinding, OrderHelpViewModel> implements OrderHelpNavigator, View.OnTouchListener {

    @Inject
    OrderHelpViewModel mOrderHelpViewModel;
    ActivityOrderHelpBinding mActivityOrderHelpBinding;
    String strOrderId;
    String message = null;
    ProgressDialog dialog;


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


        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + Uri.encode(mOrderHelpViewModel.deliveryNumber.get().trim())));
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(callIntent);

    }

    @Override
    public void gotoSupport() {

        Intent intent = SupportActivity.newIntent(OrderHelpActivity.this);
        startActivity(intent);


    }

    @Override
    public void orderCanceled() {

        if (dialog.isShowing()) dialog.show();


        Intent intent = MainActivity.newIntent(OrderHelpActivity.this);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }

    @Override
    public void orderCancelClicked() {


        if (message != null && !message.isEmpty()) {
            showAlert();
        } else {
            Toast.makeText(OrderHelpActivity.this, "Please select the reason for order cancel.", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void orderCancelFailed() {
        if (dialog.isShowing())dialog.dismiss();
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

        dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");


        mActivityOrderHelpBinding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                // mOrderHelpViewModel.cancelOrder(rb.getText().toString());

                message = rb.getText().toString();


                //   Toast.makeText(MainActivity.this, rb.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mOrderHelpViewModel.deliveryName.set(getIntent().getExtras().getString("name"));
            mOrderHelpViewModel.deliveryNumber.set(getIntent().getExtras().getString("number"));
            mOrderHelpViewModel.deliveryAssigned.set(getIntent().getExtras().getBoolean("status"));
            mOrderHelpViewModel.serviceCharges.set(getIntent().getExtras().getString("charge"));
            mOrderHelpViewModel.cancelationMessage.set(getIntent().getExtras().getString("message"));
        }

     /*   mActivityOrderHelpBinding.cancelReason1.setOnTouchListener(this);
        mActivityOrderHelpBinding.cancelReason2.setOnTouchListener(this);
        mActivityOrderHelpBinding.cancelReason3.setOnTouchListener(this);*/
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

                showAlert();

                break;
        }
        return true;
    }

    public void showAlert() {

        AlertDialog alertDialog = new AlertDialog.Builder(OrderHelpActivity.this).create();
        alertDialog.setMessage(mOrderHelpViewModel.cancelationMessage.get());
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startProgresDialog();
                        mOrderHelpViewModel.cancelOrder(message);
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerWifiReceiver();
    }

    public void startProgresDialog() {
        dialog.show();
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

