package com.tovo.eat.ui.home.homemenu;

import android.databinding.ObservableField;

import com.tovo.eat.ui.home.homemenu.kitchen.KitchenResponse;


public class OfferListItemViewModel {


    public final ObservableField<String> offerImage = new ObservableField<>();


    public final RefundListItemViewModelListener mListener;
    private final KitchenResponse.Coupon coupon;


    public OfferListItemViewModel(RefundListItemViewModelListener mListener, KitchenResponse.Coupon coupon) {
        this.mListener = mListener;
        this.coupon = coupon;
        offerImage.set(coupon.getCouponCollectionImg());
    }


    public void onItemClick() {
        if (coupon.getClickable()==null) {
            mListener.onItemClick(coupon);
        }else if (coupon.getClickable()){
            mListener.onItemClick(coupon);
        }
    }


    public interface RefundListItemViewModelListener {
        void onItemClick(KitchenResponse.Coupon result);
    }

}
