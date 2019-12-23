package com.tovo.eat.ui.home.homemenu;

import android.databinding.ObservableField;

import com.tovo.eat.ui.cart.coupon.CouponListResponse;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenResponse;


public class OfferListItemViewModel {


    public final ObservableField<String> offerImage = new ObservableField<>();


    public final RefundListItemViewModelListener mListener;
    private final KitchenResponse.Coupon refundList;


    public OfferListItemViewModel(RefundListItemViewModelListener mListener, KitchenResponse.Coupon refundList) {
        this.mListener = mListener;
        this.refundList = refundList;
        offerImage.set(refundList.getImgUrl());
    }


    public void onItemClick() {
        mListener.onItemClick(refundList);
    }


    public interface RefundListItemViewModelListener {
        void onItemClick(KitchenResponse.Coupon result);
    }

}
