/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.tovo.eat.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.di.PreferenceInfo;
import com.tovo.eat.utilities.AppConstants;

import javax.inject.Inject;

/**
 * Created by amitshekhar on 07/07/17.
 */

public class AppPreferencesHelper implements PreferencesHelper {

    private static final String PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN";

    private static final String PREF_KEY_CURRENT_USER_EMAIL = "PREF_KEY_CURRENT_USER_EMAIL";

    private static final String PREF_KEY_CURRENT_USER_ID = "PREF_KEY_CURRENT_USER_ID";

    private static final String PREF_KEY_CURRENT_USER_NAME = "PREF_KEY_CURRENT_USER_NAME";

    private static final String PREF_KEY_CURRENT_USER_PROFILE_PIC_URL = "PREF_KEY_CURRENT_USER_PROFILE_PIC_URL";

    private static final String PREF_KEY_USER_LOGGED_IN_MODE = "PREF_KEY_USER_LOGGED_IN_MODE";


    private static final String PREF_KEY_REFUND_ID = "PREF_KEY_REFUND_ID";
    private static final String PREF_KEY_COUPON_ID = "PREF_KEY_COUPON_ID";
    private static final String PREF_KEY_COUPON_CODE = "COUPON_CODE";
  private static final String PREF_KEY_RATING_SKIPS = "PREF_KEY_RATING_SKIPS";
  private static final String PREF_KEY_RATING_DATE = "PREF_KEY_RATING_DATE";
  private static final String PREF_KEY_RATING_APP_STATUS = "RATING_APP_STATUS";

    private static final String PREF_KEY_REGION_ID = "REGION_ID";
    private static final String PREF_KEY_RATING_ORDER_ID = "RATING_ORDER_ID";
    private static final String PREF_KEY_VEG_TYPE = "VEG_TYPE";

    private static final String PREF_KEY_EMAIL_STATUS = "EMAIL_STATUS";


    private static final String PREF_KEY_IS_USER_LOGGED_IN = "IS_USER_LOGGED_IN";


    private static final String PREF_KEY_SELECTED_MAKEIT_ID = "SELECTED_MAKEIT_ID";

    private static final String PREF_KEY_ORDER_ID = "ORDER_ID";
    private static final String PREF_KEY_MASTER = "MASTER";
    private static final String PREF_KEY_FILTER = "FILTER";
    private static final String PREF_KEY_STORIES_LIST = "STORIES_IST";
    private static final String PREF_KEY_CURRENT_FRAGMENT = "CURRENT_FRAGMENT";


    private static final String PREF_KEY_ADDRESS_TITLE = "ADDRESS_TITLE";
    private static final String PREF_KEY_ADDRESS_AREA = "ADDRESS_AREA";
    private static final String PREF_KEY_CURRENT_LAT = "CURRENT_LAT";
    private static final String PREF_KEY_CURRENT_LNG = "CURRENT_LNG";
    private static final String PREF_KEY_CURRENT_ADDRESS = "CURRENT_ADDRESS";
    private static final String PREF_KEY_CURRENT_ADDRESS_ID = "CURRENT_ADDRESS_ID";
    private static final String PREF_KEY_IS_FAV_CLICKED = "IS_FAV_CLICKED";

    private static final String PREF_KEY_CURRENT_USER_PHONE_NUMBER = "CURRENT_USER_PHONE_NUMBER";
    private static final String PREF_KEY_CURRENT_USER_REFERRALS = "CURRENT_USER_REFERRALS";
    private static final String PREF_KEY_GENDER_STATUS = "CURRENT_GENDER_STATUS";
    private static final String PREF_KEY_PASSWORD_STATUS = "CURRENT_PASSWORD_STATUS";
    private static final String PREF_KEY_TOTAL_ORDERS = "TOTAL_ORDERS";
    private static final String PREF_KEY_HOME_ADDRESS_ADDED = "HOME_ADDRESS_ADDED";
    private static final String PREF_KEY_OFFICE_ADDRESS_ADDED = "OFFICE_ADDRESS_ADDED";
    private static final String PREF_KEY_API_TOKEN = "API_TOKEN";


    private static final String PREF_KEY_CART = "PRODUCTS_IN_CART";

    private final SharedPreferences mPrefs;

    @Inject
    public AppPreferencesHelper(Context context, @PreferenceInfo String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public String getAccessToken() {
        return mPrefs.getString(PREF_KEY_ACCESS_TOKEN, null);
    }

    @Override
    public void setAccessToken(String accessToken) {
        mPrefs.edit().putString(PREF_KEY_ACCESS_TOKEN, accessToken).apply();
    }

    @Override
    public String getCurrentUserEmail() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_EMAIL, null);
    }

    @Override
    public void setCurrentUserEmail(String email) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_EMAIL, email).apply();
    }

    @Override
    public Integer getCurrentUserId() {
        // return userId == AppConstants.NULL_INDEX ? null : userId;
        //return mPrefs.getInt(PREF_KEY_CURRENT_USER_ID, null);

        int userId = mPrefs.getInt(PREF_KEY_CURRENT_USER_ID, AppConstants.NULL_INDEX);
        return userId;
    }

    @Override
    public void setCurrentUserId(Integer userId) {
        // Integer id = userId == null ? AppConstants.NULL_INDEX : userId;
        /*if (userId == null) {
            userId = 0;
        }
        mPrefs.edit().putInt(PREF_KEY_CURRENT_USER_ID, userId).apply();*/

        int id = userId == null ? AppConstants.NULL_INDEX : userId;
        mPrefs.edit().putInt(PREF_KEY_CURRENT_USER_ID, id).apply();
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return mPrefs.getInt(PREF_KEY_USER_LOGGED_IN_MODE,
                DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.getType());
    }

    @Override
    public void setCurrentUserLoggedInMode(DataManager.LoggedInMode mode) {
        mPrefs.edit().putInt(PREF_KEY_USER_LOGGED_IN_MODE, mode.getType()).apply();
    }

    @Override
    public String getCurrentUserName() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_NAME, null);
    }

    @Override
    public void setCurrentUserName(String userName) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_NAME, userName).apply();
    }

/*
    @Override
    public String getCurrentUserProfilePicUrl() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_PROFILE_PIC_URL, null);
    }
*/

    @Override
    public void setCurrentUserProfilePicUrl(String profilePicUrl) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_PROFILE_PIC_URL, profilePicUrl).apply();
    }

    @Override
    public boolean getIsLoggedIn() {
        return mPrefs.getBoolean(PREF_KEY_IS_USER_LOGGED_IN, false);
    }

    @Override
    public void setIsLoggedIn(boolean isLoggedIn) {
        mPrefs.edit().putBoolean(PREF_KEY_IS_USER_LOGGED_IN, isLoggedIn).apply();
    }

    @Override
    public String getCurrentAddressTitle() {
        return mPrefs.getString(PREF_KEY_ADDRESS_TITLE, null);
    }

    @Override
    public void setCurrentAddressTitle(String title) {
        mPrefs.edit().putString(PREF_KEY_ADDRESS_TITLE, title).apply();
    }

    @Override
    public String getCurrentAddressArea() {
        return mPrefs.getString(PREF_KEY_ADDRESS_AREA, null);
    }

    @Override
    public void setCurrentAddressArea(String area) {
        mPrefs.edit().putString(PREF_KEY_ADDRESS_AREA, area).apply();
    }

    @Override
    public String getCurrentAddress() {
        return mPrefs.getString(PREF_KEY_CURRENT_ADDRESS, null);
    }

    @Override
    public void setCurrentAddress(String address) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_ADDRESS, address).apply();
    }

    @Override
    public String getCurrentLat() {
        return mPrefs.getString(PREF_KEY_CURRENT_LAT, null);
    }

    @Override
    public void setCurrentLat(double lat) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_LAT, String.valueOf(lat)).apply();
    }

    @Override
    public String getCurrentLng() {
        return mPrefs.getString(PREF_KEY_CURRENT_LNG, null);
    }

    @Override
    public void setCurrentLng(double lng) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_LNG, String.valueOf(lng)).apply();
    }

    @Override
    public Integer getMakeitID() {
        return mPrefs.getInt(PREF_KEY_SELECTED_MAKEIT_ID, 0);
    }

    @Override
    public void setMakeitID(Integer id) {
        mPrefs.edit().putInt(PREF_KEY_SELECTED_MAKEIT_ID, id).apply();
    }

    @Override
    public Integer getOrderId() {
        return mPrefs.getInt(PREF_KEY_ORDER_ID, 0);
    }

    @Override
    public void setOrderId(Integer orderId) {
        mPrefs.edit().putInt(PREF_KEY_ORDER_ID, orderId).apply();
    }

    @Override
    public Integer getAddressId() {
        return mPrefs.getInt(PREF_KEY_CURRENT_ADDRESS_ID, 0);
    }

    @Override
    public void setAddressId(Integer orderId) {
        mPrefs.edit().putInt(PREF_KEY_CURRENT_ADDRESS_ID, orderId).apply();
    }

    @Override
    public String getMaster() {
        return mPrefs.getString(PREF_KEY_MASTER, null);
    }

    @Override
    public void setMaster(String master) {
        mPrefs.edit().putString(PREF_KEY_MASTER, master).apply();
    }

    @Override
    public String getFilterSort() {
        return mPrefs.getString(PREF_KEY_FILTER, null);
    }

    @Override
    public void setFilterSort(String master) {
        mPrefs.edit().putString(PREF_KEY_FILTER, master).apply();
    }

    @Override
    public String getStoriesList() {
        return mPrefs.getString(PREF_KEY_STORIES_LIST, null);
    }

    @Override
    public void setStoriesList(String stories) {
        mPrefs.edit().putString(PREF_KEY_STORIES_LIST, stories).apply();
    }

    @Override
    public Integer getCurrentFragment() {
        return mPrefs.getInt(PREF_KEY_CURRENT_FRAGMENT, 0);
    }

    @Override
    public void setCurrentFragment(Integer id) {
        mPrefs.edit().putInt(PREF_KEY_CURRENT_FRAGMENT, id).apply();
    }


    @Override
    public String getCurrentUserPhNo() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_PHONE_NUMBER, null);
    }

    @Override
    public void setCurrentUserPhNo(String phoneNumber) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_PHONE_NUMBER, phoneNumber).apply();
    }

    @Override
    public String getCurrentUserReferrals() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_REFERRALS, null);
    }

    @Override
    public void setCurrentUserReferrals(String referralCode) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_REFERRALS, referralCode).apply();
    }

    @Override
    public boolean getIsFav() {
        return mPrefs.getBoolean(PREF_KEY_IS_FAV_CLICKED, false);
    }

    @Override
    public void setIsFav(boolean status) {
        mPrefs.edit().putBoolean(PREF_KEY_IS_FAV_CLICKED, status).apply();
    }

    @Override
    public boolean getisGenderStatus() {
        return mPrefs.getBoolean(PREF_KEY_GENDER_STATUS, false);
    }

    @Override
    public void setisGenderStatus(boolean status) {
        mPrefs.edit().putBoolean(PREF_KEY_GENDER_STATUS, status).apply();
    }

    @Override
    public boolean getisPasswordStatus() {
        return mPrefs.getBoolean(PREF_KEY_PASSWORD_STATUS, false);
    }

    @Override
    public void setisPasswordStatus(boolean status) {
        mPrefs.edit().putBoolean(PREF_KEY_PASSWORD_STATUS, status).apply();
    }

    @Override
    public Integer getTotalOrders() {
        return mPrefs.getInt(PREF_KEY_TOTAL_ORDERS, 0);
    }

    @Override
    public void setTotalOrders(Integer orders) {
        mPrefs.edit().putInt(PREF_KEY_TOTAL_ORDERS, orders).apply();
    }

    @Override
    public boolean isHomeAddressAdded() {
        return mPrefs.getBoolean(PREF_KEY_HOME_ADDRESS_ADDED, false);
    }

    @Override
    public void setHomeAddressAdded(boolean status) {
        mPrefs.edit().putBoolean(PREF_KEY_HOME_ADDRESS_ADDED, status).apply();
    }

    @Override
    public boolean isOfficeAddressAdded() {
        return mPrefs.getBoolean(PREF_KEY_OFFICE_ADDRESS_ADDED, false);
    }

    @Override
    public void setOfficeAddressAdded(boolean status) {
        mPrefs.edit().putBoolean(PREF_KEY_OFFICE_ADDRESS_ADDED, status).apply();
    }

    @Override
    public int getRefundId() {
        return mPrefs.getInt(PREF_KEY_REFUND_ID, 0);
    }

    @Override
    public void setRefundId(int rcid) {
        mPrefs.edit().putInt(PREF_KEY_REFUND_ID, rcid).apply();
    }

    @Override
    public int getRegionId() {
        return mPrefs.getInt(PREF_KEY_REGION_ID, 0);
    }

    @Override
    public void setRegionId(int regiionId) {
        mPrefs.edit().putInt(PREF_KEY_REGION_ID, regiionId).apply();
    }

    @Override
    public int getCouponId() {
        return mPrefs.getInt(PREF_KEY_COUPON_ID, 0);

    }

    @Override
    public void setCouponId(int couponId) {
        mPrefs.edit().putInt(PREF_KEY_COUPON_ID, couponId).apply();
    }

    @Override
    public boolean getEmailStatus() {
        return mPrefs.getBoolean(PREF_KEY_EMAIL_STATUS, false);
    }

    @Override
    public void setEmailStatus(boolean status) {
        mPrefs.edit().putBoolean(PREF_KEY_EMAIL_STATUS, status).apply();
    }

    @Override
    public Integer getVegType() {
        return mPrefs.getInt(PREF_KEY_VEG_TYPE, 0);
    }

    @Override
    public void setVegType(Integer type) {
        mPrefs.edit().putInt(PREF_KEY_VEG_TYPE, type).apply();
    }

    @Override
    public Integer getRatingSkips() {
        return mPrefs.getInt(PREF_KEY_RATING_SKIPS, 0);
    }

    @Override
    public void setRatingSkips(Integer skips) {
        mPrefs.edit().putInt(PREF_KEY_RATING_SKIPS, skips).apply();
    }

    @Override
    public Integer getRatingOrderid() {
        return mPrefs.getInt(PREF_KEY_RATING_ORDER_ID, 0);
    }

    @Override
    public void setRatingOrderid(Integer orderid) {
        mPrefs.edit().putInt(PREF_KEY_RATING_ORDER_ID, orderid).apply();
    }

    @Override
    public String getRatingDate() {
        return mPrefs.getString(PREF_KEY_RATING_DATE, null);
    }

    @Override
    public void setRatingDate(String date) {
        mPrefs.edit().putString(PREF_KEY_RATING_DATE, date).apply();
    }

    @Override
    public boolean getRatingAppStatus() {
        return mPrefs.getBoolean(PREF_KEY_RATING_APP_STATUS, false);
    }

    @Override
    public void setRatingAppStatus(boolean status) {
        mPrefs.edit().putBoolean(PREF_KEY_RATING_APP_STATUS, status).apply();
    }

    @Override
    public String getApiToken() {
        return mPrefs.getString(PREF_KEY_API_TOKEN, "login");
    }

    @Override
    public void setApiToken(String token) {
        mPrefs.edit().putString(PREF_KEY_API_TOKEN, token).apply();
    }

    @Override
    public String getCouponCode() {
        return  mPrefs.getString(PREF_KEY_COUPON_CODE, null);
    }

    @Override
    public void setCouponCode(String coupon) {
        mPrefs.edit().putString(PREF_KEY_COUPON_CODE, coupon).apply();
    }

    @Override
    public String getCartDetails() {
        return mPrefs.getString(PREF_KEY_CART, null);
    }

    @Override
    public void setCartDetails(String jsonCart) {
        mPrefs.edit().putString(PREF_KEY_CART, jsonCart).apply();
    }

    public boolean getPrefKeyIsUserLoggedIn() {
        return mPrefs.getBoolean(PREF_KEY_IS_USER_LOGGED_IN, false);
    }
}
