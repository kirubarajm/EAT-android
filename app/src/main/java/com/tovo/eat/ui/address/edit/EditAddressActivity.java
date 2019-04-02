package com.tovo.eat.ui.address.edit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityAddAddressBinding;
import com.tovo.eat.ui.base.BaseActivity;

import javax.inject.Inject;

public class EditAddressActivity extends BaseActivity<ActivityAddAddressBinding, EditAddressViewModel> implements EditAddressNavigator {


    public ActivityAddAddressBinding mActivityAddAddressBinding;

    @Inject
    public EditAddressViewModel mEditAddressViewModel;

    public static Intent newIntent(Context context) {

        return new Intent(context, EditAddressActivity.class);
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
    public EditAddressViewModel getViewModel() {

        return mEditAddressViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityAddAddressBinding = getViewDataBinding();
        mEditAddressViewModel.setNavigator(this);


    }


}