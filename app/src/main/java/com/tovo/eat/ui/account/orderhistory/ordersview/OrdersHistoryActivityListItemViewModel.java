package com.tovo.eat.ui.account.orderhistory.ordersview;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

public class OrdersHistoryActivityListItemViewModel {

    public final ObservableField<String> orderid = new ObservableField<>();
    public final ObservableField<String> userid = new ObservableField<>();
    public final ObservableField<String> ordertime = new ObservableField<>();
    public final ObservableField<String> locality = new ObservableField<>();
    public final ObservableField<String> delivery_charge = new ObservableField<>();
    public final ObservableField<String> ordertype = new ObservableField<>();
    public final ObservableField<String> orderstatus = new ObservableField<>();
    public final ObservableField<String> gst = new ObservableField<>();
    public final ObservableField<String> coupon = new ObservableField<>();
    public final ObservableField<String> payment_type = new ObservableField<>();
    public final ObservableField<String> makeit_user_id = new ObservableField<>();
    public final ObservableField<String> moveit_user_id = new ObservableField<>();
    public final ObservableField<String> cus_lat = new ObservableField<>();
    public final ObservableField<String> cus_lon = new ObservableField<>();
    public final ObservableField<String> cus_address = new ObservableField<>();
    public final ObservableField<String> makeit_status = new ObservableField<>();
    public final ObservableField<String> moveit_reached_time = new ObservableField<>();
    public final ObservableField<String> moveit_expected_delivered_time = new ObservableField<>();
    public final ObservableField<String> moveit_actual_delivered_time = new ObservableField<>();
    public final ObservableField<String> moveit_remarks_order = new ObservableField<>();
    public final ObservableField<String> makeit_expected_preparing_time = new ObservableField<>();
    public final ObservableField<String> makeit_actual_preparing_time = new ObservableField<>();
    public final ObservableField<String> created_at = new ObservableField<>();
    public final ObservableField<String> price = new ObservableField<>();
    public final ObservableField<String> payment_status = new ObservableField<>();
    public final ObservableField<String> lock_status = new ObservableField<>();
    public final ObservableField<String> updated_at = new ObservableField<>();
    public final ObservableField<String> order_assigned_time = new ObservableField<>();


    public final ObservableField<String> quantity = new ObservableField<>();
    public final ObservableField<String> productid = new ObservableField<>();
    public final ObservableField<String> product_name = new ObservableField<>();
    public final ObservableField<String> moveitName = new ObservableField<>();
    public final ObservableField<String> makeitName = new ObservableField<>();
    public final ObservableField<String> vegType = new ObservableField<>();



    public ObservableBoolean isVeg=new ObservableBoolean();



    private  OrdersHistoryActivityResponse.Result.Item mOrderLists;


    public OrdersHistoryActivityListItemViewModel(OrdersHistoryActivityResponse.Result.Item mOrderList) {
        this.mOrderLists=mOrderList;
        this.quantity.set("x"+String.valueOf(mOrderList.getQuantity()));
        this.productid.set(String.valueOf(mOrderList.getProductid()));
        this. product_name.set(String.valueOf(mOrderList.getProductName()));
        this. price.set(String.valueOf(mOrderList.getPrice()));


        if (mOrderList.getVegtype().equals("0")){

            isVeg.set(true);

        }else {

            isVeg.set(false);
        }






    }

    public interface OrdersItemViewModelListener {

        void onItemClick(OrdersHistoryActivityResponse.Result.Item mOrderList);
    }


}
