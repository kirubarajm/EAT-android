package com.tovo.eat.ui.address.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityAddressListBinding;
import com.tovo.eat.ui.address.add.AddAddressActivity;
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
        Intent intent = AddAddressActivity.newIntent(AddressListActivity.this);
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


    private void subscribeToLiveData() {
        mAddressListViewModel.getAddrressListItemsLiveData().observe(this,
                addrressListItemViewModel -> mAddressListViewModel.addDishItemsToList(addrressListItemViewModel));
    }


    @Override
    public void onResume() {
        super.onResume();
        //  mAddressListViewModel.fetchRepos();
    }

    @Override
    public void onItemClickData(AddressListResponse.Result blogUrl) {

        mAddressListViewModel.setCurrentAddress(blogUrl);


    }

    @Override
    public void editAddressClick(AddressListResponse.Result blogUrl) {
        Intent intent = AddAddressActivity.newIntent(AddressListActivity.this);
        startActivity(intent);

    }


}

