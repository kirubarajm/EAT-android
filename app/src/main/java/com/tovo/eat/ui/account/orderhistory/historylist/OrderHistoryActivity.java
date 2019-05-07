package com.tovo.eat.ui.account.orderhistory.historylist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityOrdersHistoryListBinding;
import com.tovo.eat.ui.account.orderhistory.ordersview.OrderHistoryActivityView;
import com.tovo.eat.ui.base.BaseActivity;

import javax.inject.Inject;

public class OrderHistoryActivity extends BaseActivity<ActivityOrdersHistoryListBinding, OrderHistoryActivityViewModel> implements OrderHistoryActivityNavigator,
        OrdersHistoryActivityAdapter.OrdersHistoryAdapterListener {

    @Inject
    OrderHistoryActivityViewModel mOrderHistoryActivityViewModel;
    ActivityOrdersHistoryListBinding mActivityOrdersHistoryListBinding;
    @Inject
    OrdersHistoryActivityAdapter mOrdersHistoryActivityAdapter;
    @Inject
    LinearLayoutManager mLayoutManager;

    public static Intent newIntent(Context context) {
        return new Intent(context, OrderHistoryActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void onRefreshLayout() {
        mOrderHistoryActivityViewModel.fetchRepos(1);
    }

    @Override
    public void onRefreshSuccess() {
        mActivityOrdersHistoryListBinding.swipeOrdersHistory.setRefreshing(false);
    }

    @Override
    public void onRefreshFailure() {
        mActivityOrdersHistoryListBinding.swipeOrdersHistory.setRefreshing(false);
    }

    @Override
    public int getBindingVariable() {
        return BR.ordersHistoryViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_orders_history_list;
    }

    @Override
    public OrderHistoryActivityViewModel getViewModel() {
        return mOrderHistoryActivityViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityOrdersHistoryListBinding = getViewDataBinding();
        mOrderHistoryActivityViewModel.setNavigator(this);

        setTitle("Order history");
        Toolbar toolbar = findViewById(R.id.toolbar_history_list);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        mActivityOrdersHistoryListBinding.toolbarHistoryList.setTitle("Order history");

        mOrdersHistoryActivityAdapter.setListener(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityOrdersHistoryListBinding.recyclerviewOrders.setLayoutManager(new LinearLayoutManager(this));
        mActivityOrdersHistoryListBinding.recyclerviewOrders.setAdapter(mOrdersHistoryActivityAdapter);

        subscribeToLiveData();
    }



    private void subscribeToLiveData() {
        mOrderHistoryActivityViewModel.getOrders().observe(this,
                ordersItemViewModel -> mOrderHistoryActivityViewModel.addOrdersListItemsToList(ordersItemViewModel));
    }

    @Override
    public void listItem(OrdersHistoryListResponse.Result mOrderList) {
        Log.e("mOrderList",mOrderList.toString());

        Intent intent = OrderHistoryActivityView.newIntent(this);
        intent.putExtra("orderId",
               String.valueOf(mOrderList.getOrderid()) );
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
