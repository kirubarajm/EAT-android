package com.tovo.eat.ui.payment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityPaymentBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.orderplaced.OrderPlacedActivity;
import com.tovo.eat.ui.payment.pendingpaymentpage.PendingPaymentPageAlert;
import com.tovo.eat.ui.pendingpayment.PaymentListener;
import com.tovo.eat.ui.registration.RegistrationActivity;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.analytics.Analytics;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;

import org.json.JSONObject;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

import static com.tovo.eat.utilities.AppConstants.COD_REQUESTCODE;
import static com.tovo.eat.utilities.AppConstants.ONLINE_REQUESTCODE;

public class PaymentActivity extends BaseActivity<ActivityPaymentBinding, PaymentViewModel> implements PaymentNavigator, PaymentResultListener, HasSupportFragmentInjector, PaymentListener {

    public ActivityPaymentBinding mActivityPaymentBinding;
    @Inject
    public PaymentViewModel mPaymentViewModel;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    JSONObject options;
boolean paymentRetry=false;
    Analytics analytics;
    String  pageName="Payment page";


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

        return new Intent(context, PaymentActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.paymentViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_payment;
    }

    @Override
    public PaymentViewModel getViewModel() {

        return mPaymentViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void goBack() {
        finish();
    }

    @Override
    public void clickCard() {

    }

    @Override
    public void clickNetbanking() {

    }

    @Override
    public void clickUPI() {

    }

    @Override
    public void clickCOD() {


    }

    @Override
    public void clickwallet() {

    }

    @Override
    public void orderCompleted() {

        Intent newIntent = OrderPlacedActivity.newIntent(PaymentActivity.this);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(newIntent);
        finish();
    }

    @Override
    public void orderGenerated(Integer orderId, String customerId, Integer amount) {

        final Activity activity = this;

        final Checkout co = new Checkout();

        // co.setImage(R.mipmap.ic_launcher);

        co.setFullScreenDisable(true);
        try {
            options = new JSONObject();
            options.put("name", getString(R.string.app_name));
            options.put("description", getString(R.string.orderid) + orderId);
            //You can omit the image option to fetch the image from dashboard
            options.put("currency", "INR");
            options.put("amount", amount * 100);
            options.put("customer_id", customerId);
            JSONObject ReadOnly = new JSONObject();
            ReadOnly.put("email", "true");
            ReadOnly.put("contact", "true");
            options.put("readonly", ReadOnly);

            co.open(activity, options);

        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
        }


    }

    @Override
    public void pendingPayment(Integer orderId, String customerId, Integer amount) {

    }

    @Override
    public void paymentSuccessed(boolean status) {

        if (status) {
            Intent newIntent = OrderPlacedActivity.newIntent(PaymentActivity.this);
            newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(newIntent);
            finish();
        }

    }

    @Override
    public void postRegistration(Integer code) {
        Intent intent = RegistrationActivity.newIntent(getApplicationContext());
        intent.putExtra("type", "cart");
        intent.putExtra("amount", mPaymentViewModel.amount.get());
        startActivityForResult(intent, code);

    }

    @Override
    public void showToast(String msg) {

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityPaymentBinding = getViewDataBinding();
        mPaymentViewModel.setNavigator(this);


        analytics=new Analytics(this,pageName);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            if (intent.getExtras().getString("amount") != null) {
                mPaymentViewModel.amount.set(intent.getExtras().getString("amount"));

                new Analytics(intent.getExtras().getString("amount"));

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

       // boolean status = data.getBooleanExtra("status", false);
        if (resultCode == RESULT_OK){
            switch (requestCode) {

                case COD_REQUESTCODE:

                    mPaymentViewModel.cashMode();

                    break;

                case ONLINE_REQUESTCODE:


                    mPaymentViewModel.payOnline();

                    break;

            }
    }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerWifiReceiver();


        if (paymentRetry) showRetry();


    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterWifiReceiver();
    }

    @Override
    public void onPaymentSuccess(String s) {

        mPaymentViewModel.paymentSuccess(s, 1);






    }

    @Override
    public void onPaymentError(int i, String s) {

/*
        AlertDialog.Builder builder=new AlertDialog.Builder(PaymentActivity.this);
        builder.setTitle("Payment failed");
        builder.show();*/


        // mPaymentViewModel.paymentSuccess("Canceled", 2);


paymentRetry=true;


               // showRetry();


       /* Bundle bundle = new Bundle();
        bundle.putInt("orderid", 880);
        bundle.putString("brandname", "dddddff");
        bundle.putString("products", "dfdfff");
        bundle.putInt("price", 500);
        PendingPaymentAlert bottomSheetFragment = new PendingPaymentAlert();
        bottomSheetFragment.setArguments(bundle);
        bottomSheetFragment.setCancelable(false);
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());*/


    }


    public void showRetry() {

        try {
            Bundle bundle = new Bundle();
            PendingPaymentPageAlert bottomSheetFragment = new PendingPaymentPageAlert();
            bottomSheetFragment.setArguments(bundle);
            bottomSheetFragment.setCancelable(false);
            bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            paymentRetry=false;
        } catch (Exception ee) {
            ee.printStackTrace();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    showRetry();
                }
            },1000);
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
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public void paymentRetry() {
        final Activity activity = this;

        final Checkout co = new Checkout();

        co.setFullScreenDisable(true);
        try {
            co.open(activity, options);

        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
        }


    }

    @Override
    public void paymentCancel() {
        mPaymentViewModel.paymentSuccess("Canceled", 2);
    }


}