package com.tovo.eat.utilities.analytics;


import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.tovo.eat.utilities.MvvmApp;


public class Analytics {

    private static FirebaseAnalytics mFirebaseAnalytics;
    private String screen_name, screen_id, click;


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

        if (mFirebaseAnalytics == null)
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(MvvmApp.getInstance());

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CURRENCY, "INR");
        bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, kitchenName);
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, String.valueOf(productid));
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, productname);
        bundle.putDouble(FirebaseAnalytics.Param.PRICE, price);
        bundle.putLong(FirebaseAnalytics.Param.QUANTITY, quantity);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_TO_CART, bundle);

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

}
