package com.tovo.eat.ui.account.orderhistory.ordersview;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.tovo.eat.ui.cart.CartPageResponse;


public class OrderBillItemViewModel {

    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> charges = new ObservableField<>();
    public final ObservableField<String> note = new ObservableField<>();
    public final ObservableBoolean showInfo = new ObservableBoolean();
    public final ObservableBoolean lowCost = new ObservableBoolean();
    public final ObservableBoolean aboveCost = new ObservableBoolean();

    private final OrdersHistoryActivityResponse.Cartdetail cartdetail;

    public OrderBillItemViewModel(OrdersHistoryActivityResponse.Cartdetail cartdetail) {
        this.cartdetail = cartdetail;

        title.set(cartdetail.getTitle());
        charges.set(String.valueOf(cartdetail.getCharges()));

    }


    public interface BilldetailsInfoViewModelListener {
        void onItemClick(CartPageResponse.Cartdetail cartdetail);
    }

}
