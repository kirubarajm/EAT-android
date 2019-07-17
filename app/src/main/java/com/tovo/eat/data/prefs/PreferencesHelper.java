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


import com.tovo.eat.data.DataManager;

/**
 * Created by amitshekhar on 07/07/17.
 */

public interface PreferencesHelper {

    String getAccessToken();

    void setAccessToken(String accessToken);

    String getCurrentUserEmail();

    void setCurrentUserEmail(String email);

    Integer getCurrentUserId();

    void setCurrentUserId(Integer userId);

    int getCurrentUserLoggedInMode();

    void setCurrentUserLoggedInMode(DataManager.LoggedInMode mode);

    String getCurrentUserName();

    void setCurrentUserName(String userName);


    void setCurrentUserProfilePicUrl(String profilePicUrl);

    boolean getIsLoggedIn();

    void setIsLoggedIn(boolean isLoggedIn);

    String getCartDetails();

    void setCartDetails(String jsonCart);

    String getCurrentAddressTitle();

    void setCurrentAddressTitle(String title);


    String getCurrentAddressArea();

    void setCurrentAddressArea(String area);

    String getCurrentAddress();

    void setCurrentAddress(String area);


    String getCurrentLat();


    void setCurrentLat(double lat);


    String getCurrentLng();


    void setCurrentLng(double lng);


    Integer getMakeitID();


    void setMakeitID(Integer id);


    Integer getOrderId();


    void setOrderId(Integer orderId);


    Integer getAddressId();


    void setAddressId(Integer orderId);


    String getMaster();

    void setMaster(String master);


    String getFilterSort();

    void setFilterSort(String master);

    String getStoriesList();

    void setStoriesList(String stories);


    Integer getCurrentFragment();


    void setCurrentFragment(Integer orderId);


    String getCurrentUserPhNo();

    void setCurrentUserPhNo(String phoneNumber);

    String getCurrentUserReferrals();

    void setCurrentUserReferrals(String area);


    boolean getIsFav();

    void setIsFav(boolean status);


    boolean getisGenderStatus();

    void setisGenderStatus(boolean status);


    boolean getisPasswordStatus();

    void setisPasswordStatus(boolean status);


    Integer getTotalOrders();


    void setTotalOrders(Integer orders);






    boolean isHomeAddressAdded();

    void setHomeAddressAdded(boolean status);

    boolean isOfficeAddressAdded();

    void setOfficeAddressAdded(boolean status);



    int getRefundId();
    void setRefundId(int rcid);


    int getRegionId();
    void setRegionId(int regiionId);


    boolean getEmailStatus();

    void setEmailStatus(boolean status);

    Integer getVegType();

    void setVegType(Integer type);
}
