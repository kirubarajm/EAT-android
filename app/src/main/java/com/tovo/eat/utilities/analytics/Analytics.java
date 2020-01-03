package com.tovo.eat.utilities.analytics;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.tovo.eat.BuildConfig;
import com.tovo.eat.data.prefs.AppPreferencesHelper;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;


public class Analytics {

    private static FirebaseAnalytics mFirebaseAnalytics;
    long userid = 0L;
    private String screen_name, screen_id, click;

    public Analytics() {

        if(BuildConfig.DEBUG) return;

        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(MvvmApp.getInstance(), AppConstants.PREF_NAME);

        try {
            userid = appPreferencesHelper.getCurrentUserId();

        } catch (Exception e) {

            SharedPreferences settings = MvvmApp.getInstance().getSharedPreferences(AppConstants.PREF_NAME, Context.MODE_PRIVATE);
            // settings.edit().clear().apply();
            int uid = settings.getInt("PREF_KEY_CURRENT_USER_ID", 0);
            int aid = settings.getInt("CURRENT_ADDRESS_ID", 0);
            int oid = settings.getInt("PREF_KEY_ORDER_ID", 0);
            int roid = settings.getInt("RATING_ORDER_ID", 0);

            appPreferencesHelper.setCurrentUserId((long) uid);
            appPreferencesHelper.setAddressId((long) aid);
            appPreferencesHelper.setOrderId((long) oid);
            appPreferencesHelper.setRatingOrderid((long) roid);

        }

        if (mFirebaseAnalytics == null) {
            addProperties();
        }
    }


    public Analytics(Context context, String screen_name) {
        if(BuildConfig.DEBUG) return;

        if (mFirebaseAnalytics == null) {
            addProperties();
        }
        this.screen_name = screen_name;

        sendViewData(screen_name);
    }

    public Analytics(String screen_name) {
        if(BuildConfig.DEBUG) return;

        if (mFirebaseAnalytics == null) {
            addProperties();
        }
        this.screen_name = screen_name;

        sendViewData(screen_name);
    }

    public Analytics(int productid, String productname, int price, int quantity, String kitchenName) {
        if(BuildConfig.DEBUG) return;
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
        if(BuildConfig.DEBUG) return;
        if (mFirebaseAnalytics == null) {
            addProperties();
        }

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CURRENCY, "INR");
        bundle.putDouble(FirebaseAnalytics.Param.PRICE, price);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.BEGIN_CHECKOUT, bundle);


    }

    public Analytics(String screen_name, String click) {
        if(BuildConfig.DEBUG) return;
        if (mFirebaseAnalytics == null) {
            addProperties();
        }


       /* Bundle params = new Bundle();
        params.putString("screen_name", screen_name);
        mFirebaseAnalytics.logEvent(click, params);*/

        this.screen_name = screen_name;

        this.click = click;
        sendClickData(screen_name, click);
    }

    public void addProperties() {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(MvvmApp.getInstance());
        mFirebaseAnalytics.setUserId(String.valueOf(userid));
        mFirebaseAnalytics.setUserProperty(AppConstants.ANALYTICYS_USER_ID, String.valueOf(userid));
    }

    public void sendViewData(String screen_name) {
        Bundle params = new Bundle();
        params.putString("screen_name", screen_name);
        mFirebaseAnalytics.logEvent("event_screen_view", params);
    }

    public void sendClickData(String screen_name, String click) {
        if (mFirebaseAnalytics == null)
            addProperties();
        Bundle params = new Bundle();
        params.putString("screen_name", screen_name);
        params.putLong(AppConstants.ANALYTICYS_USER_ID, userid);
        mFirebaseAnalytics.logEvent(click, params);
    }


    public void sendClickDataWithoutUserid(String screen_name, String click) {

        if (mFirebaseAnalytics == null)
            addProperties();

        Bundle params = new Bundle();
        params.putString("screen_name", screen_name);

        mFirebaseAnalytics.logEvent(click, params);
    }


    public void addtoCart(int productid, String productName, int quantiy, int price) {

        if (mFirebaseAnalytics == null)
            addProperties();

        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.ANALYTICYS_CURRENCY_TYPE, AppConstants.ANALYTICYS_CURRENCY);
        bundle.putInt(AppConstants.ANALYTICYS_PRODUCT_PRICE, price);
        bundle.putInt(AppConstants.ANALYTICYS_PRODUCT_QUANTITY, 1);
        bundle.putInt(AppConstants.ANALYTICYS_PRODUCT_ID, productid);
        bundle.putString(AppConstants.ANALYTICYS_PRODUCT_NAME, productName);
        bundle.putLong(AppConstants.ANALYTICYS_USER_ID, userid);
        bundle.putInt(FirebaseAnalytics.Param.VALUE, price);
        mFirebaseAnalytics.logEvent(AppConstants.ANALYTICYS_ADD_TO_CART, bundle);


    }

    public void removeFromCart(int productid, String productName, int quantiy, int price) {

        if (mFirebaseAnalytics == null) {
            addProperties();
        }

        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.ANALYTICYS_CURRENCY_TYPE, AppConstants.ANALYTICYS_CURRENCY);
        bundle.putInt(AppConstants.ANALYTICYS_PRODUCT_PRICE, price);
        bundle.putInt(AppConstants.ANALYTICYS_PRODUCT_QUANTITY, 1);
        bundle.putInt(AppConstants.ANALYTICYS_PRODUCT_ID, productid);
        bundle.putString(AppConstants.ANALYTICYS_PRODUCT_NAME, productName);
        bundle.putLong(AppConstants.ANALYTICYS_USER_ID, userid);
        bundle.putInt(FirebaseAnalytics.Param.VALUE, price);
        mFirebaseAnalytics.logEvent(AppConstants.ANALYTICYS_REMOVE_FROM_CART, bundle);


    }


    public void userLogin(Long user_id, String number) {

        if (mFirebaseAnalytics == null) {
            addProperties();
        }

        Bundle bundle = new Bundle();
        bundle.putLong(AppConstants.ANALYTICYS_USER_ID, user_id);
        bundle.putString(AppConstants.ANALYTICYS_MOBILE_NUMBER, number);
        mFirebaseAnalytics.logEvent(AppConstants.ANALYTICYS_USER_LOGIN, bundle);
    }


    public void paymentFailed(Long order_id, int price) {

        if (mFirebaseAnalytics == null) {
            addProperties();
        }

        if (order_id != null) {
            Bundle bundle = new Bundle();
            bundle.putLong(AppConstants.ANALYTICYS_ORDER_ID, order_id);
            bundle.putInt(AppConstants.ANALYTICYS_PRICE, price);
            bundle.putLong(AppConstants.ANALYTICYS_USER_ID, userid);
            bundle.putInt(FirebaseAnalytics.Param.VALUE, price);
            mFirebaseAnalytics.logEvent(AppConstants.ANALYTICYS_PAYMENT_FAILED, bundle);
        }
    }

    public void paymentSuccess(Long order_id, int price) {

        if (mFirebaseAnalytics == null) {
            addProperties();
        }
        if (order_id != null) {
            Bundle bundle = new Bundle();
            bundle.putLong(AppConstants.ANALYTICYS_ORDER_ID, order_id);
            bundle.putInt(AppConstants.ANALYTICYS_PRICE, price);
            bundle.putLong(AppConstants.ANALYTICYS_USER_ID, userid);
            bundle.putInt(FirebaseAnalytics.Param.VALUE, price);
            mFirebaseAnalytics.logEvent(AppConstants.ANALYTICYS_PAYMENT_SUCCESS, bundle);
        }
    }


    public void orderPlaced(Long order_id, int price) {

        if (mFirebaseAnalytics == null) {
            addProperties();
        }

        Bundle bundle = new Bundle();
        bundle.putLong(AppConstants.ANALYTICYS_ORDER_ID, order_id);
        bundle.putInt(AppConstants.ANALYTICYS_PRICE, price);
        bundle.putLong(AppConstants.ANALYTICYS_USER_ID, userid);
        bundle.putInt(FirebaseAnalytics.Param.VALUE, price);
        mFirebaseAnalytics.logEvent(AppConstants.ANALYTICYS_ORDER_PLACED, bundle);
    }

    public void createOrder(Long order_id, int price) {

        if (mFirebaseAnalytics == null) {
            addProperties();
        }
        Bundle bundle = new Bundle();
        bundle.putLong(AppConstants.ANALYTICYS_ORDER_ID, order_id);
        bundle.putInt(AppConstants.ANALYTICYS_PRICE, price);
        bundle.putLong(AppConstants.ANALYTICYS_USER_ID, userid);
        bundle.putInt(FirebaseAnalytics.Param.VALUE, price);
        mFirebaseAnalytics.logEvent(AppConstants.ANALYTICYS_CREATE_ORDER, bundle);
    }

    public void search(String type, String name, String suggestion) {

        if (mFirebaseAnalytics == null) {
            addProperties();
        }

        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.ANALYTICYS_SEARCH_TYPE, type);
        bundle.putString(AppConstants.ANALYTICYS_SEARCH_NAME, name);
        bundle.putString(AppConstants.ANALYTICYS_SEARCH_SUGGESTION, suggestion);
        bundle.putLong(AppConstants.ANALYTICYS_USER_ID, userid);
        mFirebaseAnalytics.logEvent(AppConstants.ANALYTICYS_SEARCH_CLICKED, bundle);
    }

    public void story(int id, String title) {

        if (mFirebaseAnalytics == null) {
            addProperties();
        }

        Bundle bundle = new Bundle();
        bundle.putInt(AppConstants.ANALYTICYS_STORY_ID, id);
        bundle.putString(AppConstants.ANALYTICYS_STORY_TITLE, title);
        bundle.putLong(AppConstants.ANALYTICYS_USER_ID, userid);
        mFirebaseAnalytics.logEvent(AppConstants.ANALYTICYS_STORY_VIEW, bundle);
    }

    public void regionSelected(String title) {

        if (mFirebaseAnalytics == null) {
            addProperties();
        }

        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.ANALYTICYS_REGION, title);
        bundle.putLong(AppConstants.ANALYTICYS_USER_ID, userid);
        mFirebaseAnalytics.logEvent(AppConstants.ANALYTICYS_REGION_SELECTED, bundle);
    }


    public void appFeedback(int rating, String feedback) {

        if (mFirebaseAnalytics == null) {
            addProperties();
        }

        Bundle bundle = new Bundle();
        bundle.putInt(AppConstants.ANALYTICYS_RATING, rating);
        bundle.putString(AppConstants.ANALYTICYS_feedback, feedback);
        bundle.putLong(AppConstants.ANALYTICYS_USER_ID, userid);
        mFirebaseAnalytics.logEvent(AppConstants.SCREEN_APP_FEEDBCK, bundle);
    }


    public void queriesChat(String query, String message) {

        if (mFirebaseAnalytics == null) {
            addProperties();
        }
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.ANALYTICYS_QUERIES, query);
        bundle.putString(AppConstants.ANALYTICYS_CHAT_MESSAGE, message);
        bundle.putLong(AppConstants.ANALYTICYS_USER_ID, userid);
        mFirebaseAnalytics.logEvent(AppConstants.SCREEN_QUERY_CHAT, bundle);
    }

    public void makeQuery(String query) {

        if (mFirebaseAnalytics == null) {
            addProperties();
        }
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.ANALYTICYS_QUERIES, query);
        bundle.putLong(AppConstants.ANALYTICYS_USER_ID, userid);
        mFirebaseAnalytics.logEvent(AppConstants.ANALYTICYS_MAKE_QUERIES, bundle);
    }

    public void repeatOrder(String orderid) {

        if (mFirebaseAnalytics == null) {
            addProperties();
        }

        Bundle bundle = new Bundle();
        bundle.putLong(AppConstants.ANALYTICYS_USER_ID, userid);
        bundle.putString(AppConstants.ANALYTICYS_ORDER_ID, orderid);
        mFirebaseAnalytics.logEvent(AppConstants.ANALYTICYS_REPEAT_ORDER, bundle);
    }


    public void selectKitchen(String type, Long kitchenId) {
        if (mFirebaseAnalytics == null) {
            addProperties();
        }

        Bundle bundle = new Bundle();
        bundle.putLong(AppConstants.ANALYTICYS_KITCHEN_ID, kitchenId);
        bundle.putLong(AppConstants.ANALYTICYS_USER_ID, userid);
        mFirebaseAnalytics.logEvent(type, bundle);
    }


    public void kitchenViewcart(String type, Long kitchenid) {
        if (mFirebaseAnalytics == null) {
            addProperties();
        }

        Bundle bundle = new Bundle();
        bundle.putLong(AppConstants.ANALYTICYS_KITCHEN_ID, kitchenid);
        bundle.putLong(AppConstants.ANALYTICYS_USER_ID, userid);
        mFirebaseAnalytics.logEvent(type, bundle);
    }

    public void proceedToPay(int price) {
        if (mFirebaseAnalytics == null) {
            addProperties();
        }
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstants.ANALYTICYS_PRICE, price);
        bundle.putLong(AppConstants.ANALYTICYS_USER_ID, userid);
        bundle.putInt(FirebaseAnalytics.Param.VALUE, price);
        mFirebaseAnalytics.logEvent(AppConstants.ANALYTICYS_CHECKOUT, bundle);
    }

}
