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


import com.tovo.eat.data.prefs.PreferencesHelper;

import io.reactivex.Observable;

/**
 * Created by amitshekhar on 07/07/17.
 */

public interface DataManager extends PreferencesHelper {

    Observable<Boolean> seedDatabaseOptions();

    Observable<Boolean> seedDatabaseQuestions();

    void setUserAsLoggedOut();


    void updateApiHeader(Long userId, String accessToken);


    void updateCurrentAddress(String title, String address, double lat, double lng, String area, Integer aid);

     void updateUserInfo(String accessToken, Integer userId, LoggedInMode loggedInMode, String userName, String email, boolean isLoggedIn);

     void updateUserInfo( Integer userId);



    void saveMaster(String master);

    void saveFilterSort(String filters);

    void currentFragment(Integer id);


    void kitchenId(Integer id);


    void currentOrderId(Integer orderId);


    enum LoggedInMode {
        LOGGED_IN_MODE_LOGGED_OUT(0),
        LOGGED_IN_MODE_GOOGLE(1),
        LOGGED_IN_MODE_FB(2),
        LOGGED_IN_MODE_SERVER(3);
        private final int mType;

        LoggedInMode(int type) {
            mType = type;
        }

        public int getType() {
            return mType;
        }
    }
}
