package com.tovo.eat.ui.track.help;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityOrderDetailsBinding;
import com.tovo.eat.databinding.ActivityOrderHelpBinding;
import com.tovo.eat.ui.account.orderhistory.ordersview.OrdersHistoryActivityItemAdapter;
import com.tovo.eat.ui.account.orderhistory.ordersview.OrdersHistoryActivityResponse;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.MainActivity;

import javax.inject.Inject;

public class OrderHelpActivity extends BaseActivity<ActivityOrderHelpBinding, OrderHelpViewModel> implements OrderHelpNavigator,
        OrdersHistoryActivityItemAdapter.OrdersHistoryAdapterListener{

    @Inject
    OrderHelpViewModel mOrderHelpViewModel;
    ActivityOrderHelpBinding mActivityOrderHelpBinding;
    @Inject
    OrdersHistoryActivityItemAdapter mOrderDetailsAdapter;
    @Inject
    LinearLayoutManager mLayoutManager;
    String strOrderId;
    public static Intent newIntent(Context context) {

        return new Intent(context, OrderHelpActivity.class);
    }


    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void clearCart() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(OrderHelpActivity.this);
        builder1.setMessage("Already you have added items in cart. Do you want to clear?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                       /* Intent intent= TestActivity.newIntent(OrderHelpActivity.this);
                        intent.putExtra("cart",true);
                        startActivity(intent);*/

                       mOrderHelpViewModel.orderAvailable();

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
        Intent intent= MainActivity.newIntent(OrderHelpActivity.this);
        intent.putExtra("cart",true);
        startActivity(intent);
    }

    @Override
    public void goBack() {
       finish();
    }

    @Override
    public int getBindingVariable() {
        return BR.orderHelpViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_help;
    }

    @Override
    public OrderHelpViewModel getViewModel() {
        return mOrderHelpViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityOrderHelpBinding = getViewDataBinding();
        mOrderHelpViewModel.setNavigator(this);
        mOrderDetailsAdapter.setListener(this);




        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
           // String strOrderId=bundle.getString("orderId");
             strOrderId=getIntent().getExtras().getString("orderId");



        }


    }

    private void subscribeToLiveData() {
        mOrderHelpViewModel.getOrders().observe(this,
                ordersItemViewModel -> mOrderHelpViewModel.addOrdersListItemsToList(ordersItemViewModel));
    }


    @Override
    public void listItem(OrdersHistoryActivityResponse.Result mOrderList) {

    }
}
