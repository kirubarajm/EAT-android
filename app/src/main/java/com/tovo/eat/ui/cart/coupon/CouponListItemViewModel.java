package com.tovo.eat.ui.cart.coupon;

import android.databinding.ObservableField;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CouponListItemViewModel {


    public final ObservableField<String> code = new ObservableField<>();
    public final ObservableField<String> description = new ObservableField<>();
    public final ObservableField<String> expiry = new ObservableField<>();

    public final RefundListItemViewModelListener mListener;
    private final CouponListResponse.Result refundList;


    public CouponListItemViewModel(RefundListItemViewModelListener mListener, CouponListResponse.Result refundList) {

        this.mListener = mListener;
        this.refundList = refundList;
        code.set(refundList.getCouponName());
        description.set(refundList.getDescription());
        expiry.set("Expires " + parseDateToddMMyyyy(refundList.getExpiryDate()));
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
