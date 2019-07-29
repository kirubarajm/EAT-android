package com.tovo.eat.ui.home.homemenu;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.tovo.eat.ui.cart.coupon.CouponListResponse;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenResponse;

import java.util.List;


public class HomeOfferItemViewModel {

    public final ObservableField<String> regionName = new ObservableField<>();
    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> content = new ObservableField<>();
    public final ObservableField<String> tagline = new ObservableField<>();
    public final ObservableField<String> imageUrl = new ObservableField<>();



    public final ObservableList<CouponListResponse.Result> collections =new ObservableArrayList<>();



    public HomeOfferItemViewModel(List<CouponListResponse.Result> item_list) {

        collections.addAll(item_list);

    }


}
