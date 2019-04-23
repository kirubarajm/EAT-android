package com.tovo.eat.ui.account.orderhistory.ordersview;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityOrdersHistoryViewBinding;
import com.tovo.eat.ui.base.BaseActivity;
import javax.inject.Inject;

public class OrderHistoryActivityView extends BaseActivity<ActivityOrdersHistoryViewBinding,OrderHistoryActivityViewModelView> implements OrderHistoryActivityViewNavigator,
        OrdersHistoryActivityItemAdapter.OrdersHistoryAdapterListener{

    @Inject
    OrderHistoryActivityViewModelView mOrderHistoryActivityViewModelView;
    ActivityOrdersHistoryViewBinding mActivityOrdersHostiryViewBinding;
    @Inject
    OrdersHistoryActivityItemAdapter mOrdersHistoryActivityItemAdapter;
    @Inject
    LinearLayoutManager mLayoutManager;

    public static Intent newIntent(Context context) {

        return new Intent(context, OrderHistoryActivityView.class);
    }


    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public int getBindingVariable() {
        return BR.OrdersHistryViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_orders_history_view;
    }

    @Override
    public OrderHistoryActivityViewModelView getViewModel() {
        return mOrderHistoryActivityViewModelView;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityOrdersHostiryViewBinding = getViewDataBinding();
        mOrderHistoryActivityViewModelView.setNavigator(this);
        mOrdersHistoryActivityItemAdapter.setListener(this);

        setTitle("Orders History Details");
        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        mActivityOrdersHostiryViewBinding.toolbarHistoryView.setTitle("Orders History Details");
        mOrdersHistoryActivityItemAdapter.setListener(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityOrdersHostiryViewBinding.recyclerviewOrdersItems.setLayoutManager(new LinearLayoutManager(this));
        mActivityOrdersHostiryViewBinding.recyclerviewOrdersItems.setAdapter(mOrdersHistoryActivityItemAdapter);
        subscribeToLiveData();
    }

    private void subscribeToLiveData() {
        mOrderHistoryActivityViewModelView.getOrders().observe(this,
                ordersItemViewModel -> mOrderHistoryActivityViewModelView.addOrdersListItemsToList(ordersItemViewModel));
    }

    @Override
    public void listItem(OrdersHistoryActivityResponse.Result mOrderList) {

    }
}
