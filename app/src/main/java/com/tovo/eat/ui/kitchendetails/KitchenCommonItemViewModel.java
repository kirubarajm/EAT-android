package com.tovo.eat.ui.kitchendetails;

import android.databinding.ObservableField;

import com.tovo.eat.ui.home.kitchendish.KitchenDishResponse;


public class KitchenCommonItemViewModel {

    public final ObservableField<String> image_url = new ObservableField<>();
    public final ObservableField<Integer> type = new ObservableField<>();
    public  KitchenCommonImageItemViewModelListener mListener;
    KitchenDishResponse.Kitchenmenuimage kitchenmenuimage;

    public KitchenCommonItemViewModel(KitchenCommonImageItemViewModelListener mListener, KitchenDishResponse.Kitchenmenuimage kitchenmenuimage) {
        this.mListener = mListener;
        this.kitchenmenuimage = kitchenmenuimage;
        this.image_url.set(kitchenmenuimage.getImgUrl());
        this.type.set(kitchenmenuimage.getType());
    }

    public interface KitchenCommonImageItemViewModelListener {
        //void onItemClick();
    }

}
