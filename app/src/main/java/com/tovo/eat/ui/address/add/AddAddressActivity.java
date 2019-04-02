package com.tovo.eat.ui.address.add;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityAddAddressBinding;
import com.tovo.eat.databinding.ActivityMainBinding;
import com.tovo.eat.ui.base.BaseActivity;

import javax.inject.Inject;

public class AddAddressActivity extends BaseActivity<ActivityAddAddressBinding, AddAddressViewModel> implements AddAddressNavigator {


    public ActivityAddAddressBinding mActivityAddAddressBinding;

    @Inject
    public AddAddressViewModel mAddAddressViewModel;

    public static Intent newIntent(Context context) {

        return new Intent(context, AddAddressActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.addAddressViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_address;
    }

    @Override
    public AddAddressViewModel getViewModel() {

        return mAddAddressViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityAddAddressBinding = getViewDataBinding();
        mAddAddressViewModel.setNavigator(this);


    }


}