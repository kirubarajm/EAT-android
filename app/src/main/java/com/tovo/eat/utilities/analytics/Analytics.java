package com.tovo.eat.utilities.analytics;


import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;


public class Analytics {

    private static FirebaseAnalytics mFirebaseAnalytics;
    private String screen_name, screen_id, click;

    public Analytics() {

    }

    public Analytics(Context context, String screen_name) {
        if (mFirebaseAnalytics == null)
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        this.screen_name = screen_name;

        sendViewData(screen_name);
    }

    public Analytics(String screen_name) {
        if (mFirebaseAnalytics == null)
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(MvvmApp.getInstance());
        this.screen_name = screen_name;

        sendViewData(screen_name);
    }

    public Analytics(int productid, String productname, int price, int quantity, String kitchenName) {

     /*   if (mFirebaseAnalytics == null)
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(MvvmApp.getInstance());

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CURRENCY, "INR");
        bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, kitchenName);
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, String.valueOf(productid));
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, productname);
        bundle.putDouble(FirebaseAnalytics.Param.PRICE, price);
        bundle.putLong(FirebaseAnalytics.Param.QUANTITY, quantity);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_TO_CART, bundle);*/

    }

    public Analytics(int price) {

        if (mFirebaseAnalytics == null)
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(MvvmApp.getInstance());

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CURRENCY, "INR");
        bundle.putDouble(FirebaseAnalytics.Param.PRICE, price);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.BEGIN_CHECKOUT, bundle);


    }


    public Analytics(String screen_name, String click) {
        if (mFirebaseAnalytics == null)
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(MvvmApp.getInstance());
        this.screen_name = screen_name;

        this.click = click;
        sendClickData(screen_name, click);
    }

    private void sendViewData(String screen_name) {
        Bundle params = new Bundle();
        params.putString("screen_name", screen_name);
        mFirebaseAnalytics.logEvent("event_screen_view", params);
    }

    private void sendClickData(String screen_name, String click) {
        Bundle params = new Bundle();
        params.putString("screen_name", screen_name);
        mFirebaseAnalytics.logEvent(click, params);
    }


    public void addtoCart(int productid, String productName, int quantiy, int price) {

        if (mFirebaseAnalytics == null)
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(MvvmApp.getInstance());

        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.ANALYTICYS_CURRENCY_TYPE, AppConstants.ANALYTICYS_CURRENCY_TYPE);
        bundle.putInt(AppConstants.ANALYTICYS_PRODUCT_PRICE, price);
        bundle.putInt(AppConstants.ANALYTICYS_PRODUCT_QUANTITY, 1);
        bundle.putInt(AppConstants.ANALYTICYS_PRODUCT_ID, productid);
        bundle.putString(AppConstants.ANALYTICYS_PRODUCT_NAME, productName);
        mFirebaseAnalytics.logEvent(AppConstants.ANALYTICYS_ADD_TO_CART, bundle);


    }

    public void removeFromCart(int productid, String productName, int quantiy, int price) {

        if (mFirebaseAnalytics == null)
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(MvvmApp.getInstance());

        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.ANALYTICYS_CURRENCY_TYPE, AppConstants.ANALYTICYS_CURRENCY_TYPE);
        bundle.putInt(AppConstants.ANALYTICYS_PRODUCT_PRICE, price);
        bundle.putInt(AppConstants.ANALYTICYS_PRODUCT_QUANTITY, 1);
        bundle.putInt(AppConstants.ANALYTICYS_PRODUCT_ID, productid);
        bundle.putString(AppConstants.ANALYTICYS_PRODUCT_NAME, productName);
        mFirebaseAnalytics.logEvent(AppConstants.ANALYTICYS_REMOVE_FROM_CART, bundle);


    }


    public void userLogin(int user_id, String number) {

        if (mFirebaseAnalytics == null)
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(MvvmApp.getInstance());

        Bundle bundle = new Bundle();
        bundle.putInt(AppConstants.ANALYTICYS_USER_ID, user_id);
        bundle.putString(AppConstants.ANALYTICYS_MOBILE_NUMBER, number);
        mFirebaseAnalytics.logEvent(AppConstants.ANALYTICYS_USER_LOGIN, bundle);
    }


    public void paymentFailed(int order_id, int price) {

        if (mFirebaseAnalytics == null)
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(MvvmApp.getInstance());

        Bundle bundle = new Bundle();
        bundle.putInt(AppConstants.ANALYTICYS_ORDER_ID, order_id);
        bundle.putInt(AppConstants.ANALYTICYS_PRICE, price);
        mFirebaseAnalytics.logEvent(AppConstants.ANALYTICYS_PAYMENT_FAILED, bundle);
    }

    public void paymentSuccess(int order_id, int price) {

        if (mFirebaseAnalytics == null)
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(MvvmApp.getInstance());

        Bundle bundle = new Bundle();
        bundle.putInt(AppConstants.ANALYTICYS_ORDER_ID, order_id);
        bundle.putInt(AppConstants.ANALYTICYS_PRICE, price);
        mFirebaseAnalytics.logEvent(AppConstants.ANALYTICYS_PAYMENT_SUCCESS, bundle);
    }


    public void orderPlaced(int order_id, int price) {

        if (mFirebaseAnalytics == null)
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(MvvmApp.getInstance());

        Bundle bundle = new Bundle();
        bundle.putInt(AppConstants.ANALYTICYS_ORDER_ID, order_id);
        bundle.putInt(AppConstants.ANALYTICYS_PRICE, price);
        mFirebaseAnalytics.logEvent(AppConstants.ANALYTICYS_ORDER_PLACED, bundle);
    }

    public void proceedToPay(int order_id, int price) {

        if (mFirebaseAnalytics == null)
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(MvvmApp.getInstance());

        Bundle bundle = new Bundle();
        bundle.putInt(AppConstants.ANALYTICYS_ORDER_ID, order_id);
        bundle.putInt(AppConstants.ANALYTICYS_PRICE, price);
        mFirebaseAnalytics.logEvent(AppConstants.ANALYTICYS_PROCEED_TO_PAY, bundle);
    }

    public void search(String type, String name) {

        if (mFirebaseAnalytics == null)
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(MvvmApp.getInstance());

        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.ANALYTICYS_SEARCH_TYPE, type);
        bundle.putString(AppConstants.ANALYTICYS_SEARCH_NAME, name);
        mFirebaseAnalytics.logEvent(AppConstants.ANALYTICYS_SEARCH_CLICKED, bundle);
    }

    public void story(int id, String title) {

        if (mFirebaseAnalytics == null)
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(MvvmApp.getInstance());

        Bundle bundle = new Bundle();
        bundle.putInt(AppConstants.ANALYTICYS_STORY_ID, id);
        bundle.putString(AppConstants.ANALYTICYS_STORY_TITLE, title);
        mFirebaseAnalytics.logEvent(AppConstants.ANALYTICYS_STORY_VIEW, bundle);
    }


}
