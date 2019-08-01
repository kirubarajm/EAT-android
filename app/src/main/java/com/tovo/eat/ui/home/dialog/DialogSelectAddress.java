package com.tovo.eat.ui.home.dialog;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tovo.eat.R;
import com.tovo.eat.databinding.DialogSelectAddressBinding;
import com.tovo.eat.ui.address.list.AddressListActivity;
import com.tovo.eat.ui.base.BaseDialog;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class DialogSelectAddress extends BaseDialog implements DialogSelectAddressCallBack {

    private static final String TAG = DialogSelectAddress.class.getSimpleName();
    String strProductId = "";
    @Inject
    DialogSelectAddressViewModel mDialogSelectAddressViewModel;
    DialogSelectAddressBinding binding;
    Activity activity;


    public static DialogSelectAddress newInstance() {
        DialogSelectAddress fragment = new DialogSelectAddress();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        fragment.setCancelable(false);
        return fragment;
    }

    public void show(FragmentManager fragmentManager, Activity activity) {
        this.activity = activity;
        super.show(fragmentManager, TAG);
    }

    @Override
    public void dismissDialog() {
        dismissDialog(TAG);
    }


    @Override
    public void confirmClick() {
        dismissDialog();

        Intent intent = AddressListActivity.newIntent(getContext());
        startActivity(intent);
    }


    public void show(FragmentManager fragmentManager, String strProductId) {
        super.show(fragmentManager, TAG);
        this.strProductId = strProductId;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_select_address, container, false);
        View view = binding.getRoot();
        AndroidSupportInjection.inject(this);
        binding.setDialogSelectAddressViewModel(mDialogSelectAddressViewModel);
        mDialogSelectAddressViewModel.setNavigator(this);
        return view;
    }
}
