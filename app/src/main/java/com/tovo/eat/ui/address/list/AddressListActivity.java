package com.tovo.eat.ui.address.list;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityAddressListBinding;
import com.tovo.eat.ui.address.add.AddAddressActivity;
import com.tovo.eat.ui.address.edit.EditAddressActivity;
import com.tovo.eat.ui.base.BaseActivity;

import javax.inject.Inject;

public class AddressListActivity extends BaseActivity<ActivityAddressListBinding, AddressListViewModel> implements AddressListNavigator, AddressListAdapter.LiveProductsAdapterListener {

    @Inject
    AddressListViewModel mAddressListViewModel;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    AddressListAdapter adapter;

    ActivityAddressListBinding mActivityAddressListBinding;

    public static Intent newIntent(Context context) {

        return new Intent(context, AddressListActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityAddressListBinding = getViewDataBinding();
        mAddressListViewModel.setNavigator(this);
        adapter.setListener(this);


        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityAddressListBinding.recyclerviewList.setLayoutManager(new LinearLayoutManager(this));
        mActivityAddressListBinding.recyclerviewList.setAdapter(adapter);
        subscribeToLiveData();


        mActivityAddressListBinding.refreshList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAddressListViewModel.fetchRepos();
            }
        });


    }


    @Override
    public int getBindingVariable() {
        return BR.addressListViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_address_list;
    }

    @Override
    public AddressListViewModel getViewModel() {
        return mAddressListViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void addNewAddress() {

        Intent intent = AddAddressActivity.newIntent(AddressListActivity.this);
        startActivity(intent);
    }

    @Override
    public void editAddress() {
        Intent intent = EditAddressActivity.newIntent(AddressListActivity.this);
        //  intent.putExtra("aid",)
        startActivity(intent);
    }

    @Override
    public void listLoaded() {
        mActivityAddressListBinding.refreshList.setRefreshing(false);

    }

    @Override
    public void goBack() {
        finish();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addresDeleted() {


        mAddressListViewModel.fetchRepos();


    }


    private void subscribeToLiveData() {
        mAddressListViewModel.getAddrressListItemsLiveData().observe(this,
                addrressListItemViewModel -> mAddressListViewModel.addDishItemsToList(addrressListItemViewModel));
    }


    @Override
    public void onResume() {
        super.onResume();
        mAddressListViewModel.fetchRepos();
    }

    @Override
    public void onItemClickData(AddressListResponse.Result blogUrl) {

        mAddressListViewModel.setCurrentAddress(blogUrl);


        finish();
    }

    @Override
    public void editAddressClick(AddressListResponse.Result address) {
        Intent intent = EditAddressActivity.newIntent(AddressListActivity.this);
        intent.putExtra("aid", address.getAid());
        startActivity(intent);

    }

    @Override
    public void deleteAddress(Integer aid) {


        AlertDialog.Builder builder1 = new AlertDialog.Builder(AddressListActivity.this);
        builder1.setMessage("Are you sure want to delete?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        mAddressListViewModel.deleteAddress(aid);
                     //   mAddressListViewModel.fetchRepos();

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


}

