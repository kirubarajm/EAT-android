package com.tovo.eat.ui.home.homemenu;

import android.databinding.ObservableField;

import com.tovo.eat.ui.cart.coupon.CouponListResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


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


    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd-MMM-yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
    public interface RefundListItemViewModelListener {
        void onItemClick(CouponListResponse.Result result);
    }

}
