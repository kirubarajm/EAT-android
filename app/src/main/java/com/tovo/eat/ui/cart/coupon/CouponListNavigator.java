package com.tovo.eat.ui.cart.coupon;

public interface CouponListNavigator {

    void handleError(Throwable throwable);

    void listLoaded();

    void goBack();

    void noList();

    void showToast(String msg);

    void couponValid(Integer cid);


}
