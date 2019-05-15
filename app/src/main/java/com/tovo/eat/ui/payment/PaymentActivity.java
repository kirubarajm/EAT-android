package com.tovo.eat.ui.payment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityAddAddressBinding;
import com.tovo.eat.databinding.ActivityPaymentBinding;
import com.tovo.eat.ui.base.BaseActivity;

import javax.inject.Inject;

public class PaymentActivity extends BaseActivity<ActivityPaymentBinding, PaymentViewModel> implements PaymentNavigator {


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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityPaymentBinding = getViewDataBinding();
        mPaymentViewModel.setNavigator(this);





       /* YourEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                int len=s.toString().length();

                if (before == 0 && len == 2)
                    YourEditText.append("/");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/






    }


    @Override
    protected void onResume() {
        super.onResume();

    }


}