package com.tovo.eat.ui.address.select;

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

public class SelectSelectAddressListActivity extends BaseActivity<ActivityAddressListBinding, SelectAddressListViewModel> implements SelectAddressListNavigator, SelectAddressListAdapter.LiveProductsAdapterListener {

    @Inject
    SelectAddressListViewModel mSelectAddressListViewModel;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    SelectAddressListAdapter adapter;

    ActivityAddressListBinding mActivityAddressListBinding;

    public static Intent newIntent(Context context) {

        return new Intent(context, SelectSelectAddressListActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityAddressListBinding = getViewDataBinding();
        mSelectAddressListViewModel.setNavigator(this);
        adapter.setListener(this);


        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityAddressListBinding.recyclerviewList.setLayoutManager(new LinearLayoutManager(this));
        mActivityAddressListBinding.recyclerviewList.setAdapter(adapter);
        subscribeToLiveData();


        mActivityAddressListBinding.refreshList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSelectAddressListViewModel.fetchRepos();
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
    public SelectAddressListViewModel getViewModel() {
        return mSelectAddressListViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void addNewAddress() {

        Intent intent = AddAddressActivity.newIntent(SelectSelectAddressListActivity.this);
        startActivity(intent);
    }

    @Override
    public void editAddress() {
        Intent intent = AddAddressActivity.newIntent(SelectSelectAddressListActivity.this);
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
        mSelectAddressListViewModel.getAddrressListItemsLiveData().observe(this,
                addrressListItemViewModel -> mSelectAddressListViewModel.addDishItemsToList(addrressListItemViewModel));
    }


    @Override
    public void onResume() {
        super.onResume();
        //  mSelectAddressListViewModel.fetchRepos();
    }

    @Override
    public void onItemClickData(SelectAddressListResponse.Result blogUrl) {

    }

    @Override
    public void editAddressClick(SelectAddressListResponse.Result blogUrl) {
        Intent intent = AddAddressActivity.newIntent(SelectSelectAddressListActivity.this);
        startActivity(intent);

    }


}

