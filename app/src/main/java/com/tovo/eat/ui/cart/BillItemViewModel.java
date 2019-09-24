package com.tovo.eat.ui.cart;

import android.databinding.ObservableField;


public class BillItemViewModel {

    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> charges = new ObservableField<>();
    private final CartPageResponse.Cartdetail cartdetail;

    public BillItemViewModel(CartPageResponse.Cartdetail cartdetail) {
        this.cartdetail = cartdetail;

        title.set(cartdetail.getTitle());
        charges.set(String.valueOf(cartdetail.getCharges()));
    }
}
