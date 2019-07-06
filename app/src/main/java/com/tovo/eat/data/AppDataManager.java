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

package com.tovo.eat.data;

import android.content.Context;

import com.google.android.gms.common.api.Api;
import com.google.gson.Gson;
import com.tovo.eat.data.prefs.PreferencesHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by amitshekhar on 07/07/17.
 */
@Singleton
public class AppDataManager implements DataManager {

    private final Context mContext;

    private final Gson mGson;

    private Api mApiHelper;

    private PreferencesHelper mPreferencesHelper;

    @Inject
    public AppDataManager(Context context, Gson gson, PreferencesHelper preferencesHelper) {
        mContext = context;
        mGson = gson;
        mPreferencesHelper = preferencesHelper;
        //mApiHelper = mApiHelper;
    }

    @Override
    public Observable<Boolean> seedDatabaseOptions() {
        return null;
    }

    @Override
    public Observable<Boolean> seedDatabaseQuestions() {
        return null;
    }

    @Override
    public void setUserAsLoggedOut() {
        updateUserInfo(
                null,
                null,
                DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT,
                null,
                null,
                false);
    }

    @Override
    public void setLogout() {
        updateUserInformation(null, null, null, null, null);
    }

    @Override
    public void updateApiHeader(Long userId, String accessToken) {

    }

    @Override
    public void updateUserInfo
            (String accessToken,
             Integer userId,
             LoggedInMode loggedInMode,
             String userName,
             String email, boolean isLoggedIn
            ) {
        setAccessToken(accessToken);
        setCurrentUserId(userId);
        setCurrentUserLoggedInMode(loggedInMode);
        setCurrentUserName(userName);
        setCurrentUserEmail(email);
        setIsLoggedIn(isLoggedIn);

        /*setCurrentUserProfilePicUrl(profilePicPath)*/
        ;
    }

    @Override
    public void updateUserInformation(Integer userId, String userName, String userEmail, String PhoneNumber, String referralCode) {
        setCurrentUserId(userId);
        setCurrentUserName(userName);
        setCurrentUserEmail(userEmail);
        setCurrentUserPhNo(PhoneNumber);
        setCurrentUserReferrals(referralCode);

    }

    @Override
    public void updateUserGender(boolean genderStatus) {
        setisGenderStatus(genderStatus);
    }

    @Override
    public void updateEmailStatus(boolean status) {
        setEmailStatus(status);
    }

    @Override
    public void updateUserPasswordStatus(boolean passwordStatus) {


        setisPasswordStatus(passwordStatus);

    }

    @Override
    public void updateCurrentAddress(String title, String address, double lat, double lng, String area, Integer aid) {
        setCurrentAddressTitle(title);
        setCurrentAddressArea(area);
        setCurrentAddress(address);
        setCurrentLat(lat);
        setCurrentLng(lng);
        setCurrentAddressTitle(title);
        setCurrentAddressArea(area);
        setCurrentAddress(address);
        setCurrentLat(lat);
        setCurrentLng(lng);
        setAddressId(aid);
    }

    @Override
    public void saveMaster(String master) {
        setMaster(master);
    }

    @Override
    public void saveFilterSort(String filters) {
        setFilterSort(filters);
    }

    @Override
    public void saveVegType(String type) {
        setVegType(type);
    }

    @Override
    public void currentFragment(Integer id) {
        setCurrentFragment(id);

    }


    @Override
    public void kitchenId(Integer id) {
        setMakeitID(id);
    }

    @Override
    public void totalOrders(Integer orders) {
        setTotalOrders(orders);
    }

    @Override
    public void saveRefundId(int rcid) {
        mPreferencesHelper.setRefundId(rcid);
    }

    @Override
    public void saveRegionId(int regionid) {
        setRegionId(regionid);
    }

    @Override
    public void currentOrderId(Integer orderId) {

        mPreferencesHelper.getOrderId();
    }

    @Override
    public boolean homeAddressadded(boolean status) {
        return false;
    }

    @Override
    public boolean officeAddressadded(boolean status) {
        return false;
    }

    @Override
    public void isFavClicked(boolean status) {
        setIsFav(status);
    }

   /* @Override
    public Call<LoginResponse> userContinueClick(String name, String job) {
        return null;
    }

    @Override
    public Single<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest request) {
        return mApiHelper.doServerLoginApiCall(request);
    }*/

    @Override
    public String getAccessToken() {
        return null;
    }

    @Override
    public void setAccessToken(String accessToken) {

    }

    @Override
    public String getCurrentUserEmail() {
        return mPreferencesHelper.getCurrentUserEmail();
    }

    @Override
    public void setCurrentUserEmail(String email) {
        mPreferencesHelper.setCurrentUserEmail(email);
    }

    @Override
    public Integer getCurrentUserId() {
        return mPreferencesHelper.getCurrentUserId();
    }

    @Override
    public void setCurrentUserId(Integer userId) {
        mPreferencesHelper.setCurrentUserId(userId);
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return 0;
    }

    @Override
    public void setCurrentUserLoggedInMode(LoggedInMode mode) {

    }

    @Override
    public String getCurrentUserName() {
        return mPreferencesHelper.getCurrentUserName();
    }

    @Override
    public void setCurrentUserName(String userName) {
        mPreferencesHelper.setCurrentUserName(userName);
    }

    /*
        @Override
        public String getCurrentUserProfilePicUrl() {
            return null;
        }
    */
    @Override
    public void setCurrentUserProfilePicUrl(String profilePicUrl) {

    }

    @Override
    public boolean getIsLoggedIn() {
        return mPreferencesHelper.getIsLoggedIn();
    }

    @Override
    public void setIsLoggedIn(boolean isLoggedIn) {
        mPreferencesHelper.setIsLoggedIn(isLoggedIn);
    }

    @Override
    public String getCurrentAddressTitle() {
        return mPreferencesHelper.getCurrentAddressTitle();
    }

    @Override
    public void setCurrentAddressTitle(String title) {
        mPreferencesHelper.setCurrentAddressTitle(title);
    }

    @Override
    public String getCurrentAddressArea() {
        return mPreferencesHelper.getCurrentAddressArea();
    }

    @Override
    public void setCurrentAddressArea(String area) {
        mPreferencesHelper.setCurrentAddressArea(area);
    }

    @Override
    public String getCurrentAddress() {
        return mPreferencesHelper.getCurrentAddress();
    }

    @Override
    public void setCurrentAddress(String address) {
        mPreferencesHelper.setCurrentAddress(address);
    }

    @Override
    public String getCurrentLat() {
        return mPreferencesHelper.getCurrentLat();
    }

    @Override
    public void setCurrentLat(double lat) {
        mPreferencesHelper.setCurrentLat(lat);
    }

    @Override
    public String getCurrentLng() {
        return mPreferencesHelper.getCurrentLng();
    }

    @Override
    public void setCurrentLng(double lng) {
        mPreferencesHelper.setCurrentLng(lng);

    }

    @Override
    public Integer getMakeitID() {
        return mPreferencesHelper.getMakeitID();
    }

    @Override
    public void setMakeitID(Integer lng) {
        mPreferencesHelper.setMakeitID(lng);
    }

    @Override
    public Integer getOrderId() {
        return mPreferencesHelper.getOrderId();
    }

    @Override
    public void setOrderId(Integer orderId) {
        mPreferencesHelper.setOrderId(orderId);
    }

    @Override
    public Integer getAddressId() {
        return mPreferencesHelper.getAddressId();
    }

    @Override
    public void setAddressId(Integer orderId) {
        mPreferencesHelper.setAddressId(orderId);
    }

    @Override
    public String getMaster() {
        return mPreferencesHelper.getMaster();
    }

    @Override
    public void setMaster(String master) {
        mPreferencesHelper.setMaster(master);
    }

    @Override
    public String getFilterSort() {
        return mPreferencesHelper.getFilterSort();
    }

    @Override
    public void setFilterSort(String filters) {
        mPreferencesHelper.setFilterSort(filters);
    }

    @Override
    public Integer getCurrentFragment() {
        return mPreferencesHelper.getCurrentFragment();
    }

    @Override
    public void setCurrentFragment(Integer id) {
        mPreferencesHelper.setCurrentFragment(id);
    }

    @Override
    public String getCurrentUserPhNo() {
        return mPreferencesHelper.getCurrentUserPhNo();
    }

    @Override
    public void setCurrentUserPhNo(String phoneNumber) {
        mPreferencesHelper.setCurrentUserPhNo(phoneNumber);
    }

    @Override
    public String getCurrentUserReferrals() {
        return mPreferencesHelper.getCurrentUserReferrals();
    }

    @Override
    public void setCurrentUserReferrals(String referralCode) {
        mPreferencesHelper.setCurrentUserReferrals(referralCode);
    }

    @Override
    public boolean getIsFav() {
        return mPreferencesHelper.getIsFav();
    }

    @Override
    public void setIsFav(boolean status) {
        mPreferencesHelper.setIsFav(status);
    }

    @Override
    public boolean getisGenderStatus() {
        return mPreferencesHelper.getisGenderStatus();
    }

    @Override
    public void setisGenderStatus(boolean status) {
        mPreferencesHelper.setisGenderStatus(status);
    }

    @Override
    public boolean getisPasswordStatus() {
        return mPreferencesHelper.getisPasswordStatus();
    }

    @Override
    public void setisPasswordStatus(boolean status) {
        mPreferencesHelper.setisPasswordStatus(status);
    }

    @Override
    public Integer getTotalOrders() {
        return mPreferencesHelper.getTotalOrders();
    }

    @Override
    public void setTotalOrders(Integer orders) {
        mPreferencesHelper.setTotalOrders(orders);
    }

    @Override
    public boolean isHomeAddressAdded() {
        return mPreferencesHelper.isHomeAddressAdded();
    }

    @Override
    public void setHomeAddressAdded(boolean status) {

        mPreferencesHelper.setHomeAddressAdded(status);
    }

    @Override
    public boolean isOfficeAddressAdded() {
        return mPreferencesHelper.isOfficeAddressAdded();
    }

    @Override
    public void setOfficeAddressAdded(boolean status) {
        mPreferencesHelper.setOfficeAddressAdded(status);
    }

    @Override
    public int getRefundId() {
        return mPreferencesHelper.getRefundId();
    }

    @Override
    public void setRefundId(int rcid) {
        mPreferencesHelper.setRefundId(rcid);
    }

    @Override
    public int getRegionId() {
        return mPreferencesHelper.getRegionId();
    }

    @Override
    public void setRegionId(int regiionId) {

        mPreferencesHelper.setRegionId(regiionId);

    }

    @Override
    public boolean getEmailStatus() {
        return mPreferencesHelper.getEmailStatus();
    }

    @Override
    public void setEmailStatus(boolean status) {
        mPreferencesHelper.setEmailStatus(status);
    }

    @Override
    public String getVegType() {
        return mPreferencesHelper.getVegType();
    }

    @Override
    public void setVegType(String type) {
        mPreferencesHelper.setVegType(type);
    }


    @Override
    public String getCartDetails() {
        return mPreferencesHelper.getCartDetails();
    }

    @Override
    public void setCartDetails(String jsonCart) {
        mPreferencesHelper.setCartDetails(jsonCart);
    }
}
