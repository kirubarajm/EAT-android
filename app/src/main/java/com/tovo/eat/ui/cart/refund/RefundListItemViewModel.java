package com.tovo.eat.ui.cart.refund;

import android.databinding.ObservableField;


public class RefundListItemViewModel {


    public final ObservableField<String> code = new ObservableField<>();
    public final ObservableField<String> amount = new ObservableField<>();


    public final RefundListItemViewModelListener mListener;
    private final RefundListResponse.Result refundList;


    public RefundListItemViewModel(RefundListItemViewModelListener mListener, RefundListResponse.Result refundList) {
        this.mListener = mListener;
        this.refundList = refundList;
        code.set(refundList.getRcoupon());
        amount.set(String.valueOf(refundList.getRefundamount()));

    }

    public void onItemClick() {
        mListener.onItemClick(refundList);

    }

    public interface RefundListItemViewModelListener {

        void onItemClick(RefundListResponse.Result result);

    }

}
