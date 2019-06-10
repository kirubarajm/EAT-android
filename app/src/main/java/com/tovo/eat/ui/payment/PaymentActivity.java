package com.tovo.eat.ui.payment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.webkit.WebBackForwardList;
import android.webkit.WebView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultListener;
import com.razorpay.PaymentResultWithDataListener;
import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityAddAddressBinding;
import com.tovo.eat.databinding.ActivityPaymentBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.home.homemenu.HomeTabFragment;
import com.tovo.eat.ui.orderplaced.OrderPlacedActivity;
import com.tovo.eat.ui.registration.RegistrationActivity;

import org.json.JSONObject;

import javax.inject.Inject;

import static com.tovo.eat.utilities.AppConstants.CART_REQUESTCODE;
import static com.tovo.eat.utilities.AppConstants.COD_REQUESTCODE;
import static com.tovo.eat.utilities.AppConstants.ONLINE_REQUESTCODE;

public class PaymentActivity extends BaseActivity<ActivityPaymentBinding, PaymentViewModel> implements PaymentNavigator, PaymentResultListener {


    public ActivityPaymentBinding mActivityPaymentBinding;
    @Inject
    public PaymentViewModel mPaymentViewModel;


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
            JSONObject options = new JSONObject();
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


        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            if (intent.getExtras().getString("amount") != null) {
                mPaymentViewModel.amount.set(intent.getExtras().getString("amount"));
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        boolean status = data.getBooleanExtra("status", false);
        if (status) {

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
        protected void onResume () {
            super.onResume();

        }


        @Override
        public void onPaymentSuccess (String s){

            mPaymentViewModel.paymentSuccess(s, 1);

        }

        @Override
        public void onPaymentError ( int i, String s){
            mPaymentViewModel.paymentSuccess("Canceled", 2);
        }


    }