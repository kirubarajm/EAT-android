package com.tovo.eat.ui.track.orderdetails;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityOrderDetailsBinding;
import com.tovo.eat.databinding.ActivityOrdersHistoryViewBinding;
import com.tovo.eat.ui.account.orderhistory.historylist.OrderHistoryActivity;
import com.tovo.eat.ui.account.orderhistory.historylist.OrdersHistoryActivityAdapter;
import com.tovo.eat.ui.account.orderhistory.historylist.OrdersHistoryListResponse;
import com.tovo.eat.ui.account.orderhistory.ordersview.OrdersHistoryActivityItemAdapter;
import com.tovo.eat.ui.account.orderhistory.ordersview.OrdersHistoryActivityResponse;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.MainActivity;

import javax.inject.Inject;

public class OrderDetailsActivity extends BaseActivity<ActivityOrderDetailsBinding, OrderDetailsViewModel> implements OrderDetailsNavigator,
        OrdersHistoryActivityItemAdapter.OrdersHistoryAdapterListener{

    @Inject
    OrderDetailsViewModel mOrderDetailsViewModel;
    ActivityOrderDetailsBinding mActivityOrdersHostiryViewBinding;
    @Inject
    OrdersHistoryActivityItemAdapter mOrderDetailsAdapter;
    @Inject
    LinearLayoutManager mLayoutManager;

    public static Intent newIntent(Context context) {

        return new Intent(context, OrderDetailsActivity.class);
    }


    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void clearCart() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(OrderDetailsActivity.this);
        builder1.setMessage("Already you have added items in cart. Do you want to clear?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                       /* Intent intent= TestActivity.newIntent(OrderDetailsActivity.this);
                        intent.putExtra("cart",true);
                        startActivity(intent);*/

                       mOrderDetailsViewModel.orderAvailable();

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
        Intent intent= MainActivity.newIntent(OrderDetailsActivity.this);
        intent.putExtra("cart",true);
        startActivity(intent);
    }

    @Override
    public void goBack() {
       finish();
    }

    @Override
    public int getBindingVariable() {
        return BR.orderDetailsViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_details;
    }

    @Override
    public OrderDetailsViewModel getViewModel() {
        return mOrderDetailsViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityOrdersHostiryViewBinding = getViewDataBinding();
        mOrderDetailsViewModel.setNavigator(this);
        mOrderDetailsAdapter.setListener(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
           // String strOrderId=bundle.getString("orderId");
            String strOrderId=getIntent().getExtras().getString("orderId");

            mOrderDetailsViewModel.fetchRepos(strOrderId);

        }



        mOrderDetailsAdapter.setListener(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityOrdersHostiryViewBinding.recyclerviewOrdersItems.setLayoutManager(new LinearLayoutManager(this));
        mActivityOrdersHostiryViewBinding.recyclerviewOrdersItems.setAdapter(mOrderDetailsAdapter);
        subscribeToLiveData();
    }

    private void subscribeToLiveData() {
        mOrderDetailsViewModel.getOrders().observe(this,
                ordersItemViewModel -> mOrderDetailsViewModel.addOrdersListItemsToList(ordersItemViewModel));
    }


    @Override
    public void listItem(OrdersHistoryActivityResponse.Result mOrderList) {

    }
}
