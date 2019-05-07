package com.tovo.eat.ui.account.orderhistory.ordersview;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityOrdersHistoryViewBinding;
import com.tovo.eat.ui.address.list.AddressListActivity;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.home.kitchendish.KitchenDishActivity;

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
    public void clearCart() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(OrderHistoryActivityView.this);
        builder1.setMessage("Already you have added items in cart. Do you want to clear?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                       /* Intent intent= MainActivity.newIntent(OrderHistoryActivityView.this);
                        intent.putExtra("cart",true);
                        startActivity(intent);*/

                       mOrderHistoryActivityViewModelView.orderAvailable();

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

    @Override
    public void orderRepeat() {
        Intent intent= MainActivity.newIntent(OrderHistoryActivityView.this);
        intent.putExtra("cart",true);
        startActivity(intent);
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

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
           // String strOrderId=bundle.getString("orderId");
            String strOrderId=getIntent().getExtras().getString("orderId");

            mOrderHistoryActivityViewModelView.fetchRepos(strOrderId);

        }
        setTitle("Order history view");
        Toolbar toolbar = findViewById(R.id.toolbar_history_view);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        mActivityOrdersHostiryViewBinding.toolbarHistoryView.setTitle("Order history view");


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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

}
