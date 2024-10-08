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

package com.tovo.eat.ui.home;

public interface MainNavigator {

    void handleError(Throwable throwable);

    void openCart(String screenName);

    void disConnectGPS();

    void openHome();

    void openExplore();

    void paymentStausChanged();

    void paymentPending(Long orderid, String brandname, int price, String products);

    void openAccount();

    void selectAddress();

    void trackLiveOrder(long orderId);
    void retryPaymentForSamePrderID();

    void showOrderRating(Long orderId, String brandname);

    void update(boolean update,boolean forceupdate);

    void showPromotions(String url,boolean fullScreen, int type);
}
