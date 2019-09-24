package com.tovo.eat.ui.account.orderhistory.ordersview;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

public class OrdersHistoryActivityListItemViewModel {

    public final ObservableField<String> orderid = new ObservableField<>();
    public final ObservableField<String> userid = new ObservableField<>();
    public final ObservableField<String> locality = new ObservableField<>();
    public final ObservableField<String> delivery_charge = new ObservableField<>();
    public final ObservableField<String> orderstatus = new ObservableField<>();
    public final ObservableField<String> gst = new ObservableField<>();
    public final ObservableField<String> coupon = new ObservableField<>();
    public final ObservableField<String> created_at = new ObservableField<>();
    public final ObservableField<String> price = new ObservableField<>();
    public final ObservableField<String> payment_status = new ObservableField<>();
    public final ObservableField<String> updated_at = new ObservableField<>();
    public final ObservableField<String> quantity = new ObservableField<>();
    public final ObservableField<String> productid = new ObservableField<>();
    public final ObservableField<String> product_name = new ObservableField<>();
    public ObservableBoolean isVeg = new ObservableBoolean();

    private OrdersHistoryActivityResponse.Result.Item mOrderLists;

    public OrdersHistoryActivityListItemViewModel(OrdersHistoryActivityResponse.Result.Item mOrderList) {
        this.mOrderLists = mOrderList;
        this.quantity.set("x" + mOrderList.getQuantity());
        this.productid.set(String.valueOf(mOrderList.getProductid()));
        this.product_name.set(String.valueOf(mOrderList.getProductName()));
        this.price.set(String.valueOf(mOrderList.getPrice()));
        if (mOrderList.getVegtype().equals("0")) {

            isVeg.set(true);

        } else {

            isVeg.set(false);
        }


    }

    public interface OrdersItemViewModelListener {

        void onItemClick(OrdersHistoryActivityResponse.Result.Item mOrderList);
    }


}
