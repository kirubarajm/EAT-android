package com.tovo.eat.ui.account.orderhistory.historylist;

import android.databinding.ObservableField;

public class OrdersHistoryListItemModel {

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

    public final ObservableField<String> moveitName = new ObservableField<>();
    public final ObservableField<String> makeitName = new ObservableField<>();
    public final ObservableField<String> productsItems = new ObservableField<>();
    public final ObservableField<String> makeitLocality = new ObservableField<>();

    public OrdersHistoryListItemModel.OrdersItemViewModelListener mListener;
    String strProItems = "";
    private OrdersHistoryListResponse.Result mOrderList;

    public OrdersHistoryListItemModel(OrdersHistoryListResponse.Result orders, OrdersHistoryListItemModel.OrdersItemViewModelListener listener) {

        this.mListener = listener;
        this.mOrderList = orders;

        this.orderid.set(String.valueOf(orders.getOrderid()));
        this.userid.set(String.valueOf(orders.getUserid()));
        this.ordertime.set(String.valueOf(orders.getOrdertime()));
        this.locality.set(String.valueOf(orders.getLocality()));
        this.delivery_charge.set(String.valueOf(orders.getDeliveryCharge()));
        this.ordertype.set(String.valueOf(orders.getOrdertype()));
        this.orderstatus.set(String.valueOf(orders.getOrderstatus()));
        this.gst.set(String.valueOf(orders.getGst()));
        this.coupon.set(String.valueOf(orders.getCoupon()));
        this.payment_type.set(String.valueOf(orders.getPaymentType()));
        this.makeit_user_id.set(String.valueOf(orders.getMakeitUserId()));
        this.moveit_user_id.set(String.valueOf(orders.getMoveitUserId()));
        this.cus_lat.set(String.valueOf(orders.getCusLat()));
        this.cus_lon.set(String.valueOf(orders.getCusLon()));
        this.cus_address.set(String.valueOf(orders.getCusAddress()));
        this.makeit_status.set(String.valueOf(orders.getMakeitStatus()));
        this.moveit_reached_time.set(String.valueOf(orders.getMoveitReachedTime()));
        this.moveit_expected_delivered_time.set(String.valueOf(orders.getMoveitExpectedDeliveredTime()));
        this.moveit_actual_delivered_time.set(String.valueOf(orders.getMoveitActualDeliveredTime()));
        this.moveit_remarks_order.set(String.valueOf(orders.getMoveitRemarksOrder()));
        this.makeit_expected_preparing_time.set(String.valueOf(orders.getMakeitExpectedPreparingTime()));
        this.makeit_actual_preparing_time.set(String.valueOf(orders.getMakeitActualPreparingTime()));
        this.created_at.set(String.valueOf(orders.getCreatedAt()));
        this.price.set("INR."+orders.getPrice());
        this.payment_status.set(String.valueOf(orders.getPaymentStatus()));
        this.lock_status.set(String.valueOf(orders.getLockStatus()));
        this.updated_at.set(String.valueOf(orders.getUpdatedAt()));
        this.order_assigned_time.set(String.valueOf(orders.getOrderAssignedTime()));

        this.moveitName.set(mOrderList.getMoveitdetail().getName());

        this.makeitName.set(mOrderList.getMakeitdetail().getName());
        this.makeitLocality.set(mOrderList.getMakeitdetail().getAddress()+" | ");

        if (orders.getItems() != null && orders.getItems().size() > 0) {
            StringBuilder strItems = new StringBuilder();
            for (int i = 0; i < orders.getItems().size(); i++) {
                if (orders.getItems().get(i).productName != null) {
                    strItems.append(",").append(orders.getItems().get(i).productName);
                    strProItems = strItems.substring(1);
                    this.productsItems.set(String.valueOf(strProItems));
                }
            }
        }
    }

    public void onItemClick() {
        mListener.onItemClick(mOrderList);
    }

    public interface OrdersItemViewModelListener {

        void onItemClick(OrdersHistoryListResponse.Result mOrderList);
    }
}
