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


    private static final String PREF_KEY_IS_USER_LOGGED_IN = "IS_USER_LOGGED_IN";


    private static final String PREF_KEY_SELECTED_MAKEIT_ID = "SELECTED_MAKEIT_ID";

    private static final String PREF_KEY_ORDER_ID = "ORDER_ID";
    private static final String PREF_KEY_MASTER= "MASTER";
    private static final String PREF_KEY_FILTER= "FILTER";


    private static final String PREF_KEY_ADDRESS_TITLE = "ADDRESS_TITLE";
    private static final String PREF_KEY_ADDRESS_AREA = "ADDRESS_AREA";
    private static final String PREF_KEY_CURRENT_LAT = "CURRENT_LAT";
    private static final String PREF_KEY_CURRENT_LNG = "CURRENT_LNG";
    private static final String PREF_KEY_CURRENT_ADDRESS = "CURRENT_ADDRESS";
    private static final String PREF_KEY_CURRENT_ADDRESS_ID = "CURRENT_ADDRESS_ID";


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
       /* long userId = mPrefs.getLong(PREF_KEY_CURRENT_USER_ID, AppConstants.NULL_INDEX);
        return userId == AppConstants.NULL_INDEX ? null : userId;*/
       return 2;
    }

    @Override
    public void setCurrentUserId(Integer userId) {
       // Integer id = userId == null ? AppConstants.NULL_INDEX : userId;
        mPrefs.edit().putInt(PREF_KEY_CURRENT_USER_ID, userId).apply();
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
        return mPrefs.getString(PREF_KEY_ADDRESS_TITLE, "Select Address");
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
        return  mPrefs.getString(PREF_KEY_CURRENT_ADDRESS, null);
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
        return mPrefs.getString(PREF_KEY_FILTER, "");
    }

    @Override
    public void setFilterSort(String master) {
        mPrefs.edit().putString(PREF_KEY_FILTER, master).apply();
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
