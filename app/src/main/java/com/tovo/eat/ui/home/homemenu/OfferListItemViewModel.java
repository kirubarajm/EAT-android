package com.tovo.eat.ui.home.homemenu;

import android.databinding.ObservableField;

import com.tovo.eat.ui.cart.coupon.CouponListResponse;


public class OfferListItemViewModel {


    public final ObservableField<String> offerImage = new ObservableField<>();


    public final RefundListItemViewModelListener mListener;
    private final CouponListResponse.Result refundList;


    public OfferListItemViewModel(RefundListItemViewModelListener mListener, CouponListResponse.Result refundList) {
        this.mListener = mListener;
        this.refundList = refundList;
        offerImage.set(refundList.getImgUrl());
    }


    public void onItemClick() {
        mListener.onItemClick(refundList);
    }


    public interface RefundListItemViewModelListener {
        void onItemClick(CouponListResponse.Result result);
    }

}
