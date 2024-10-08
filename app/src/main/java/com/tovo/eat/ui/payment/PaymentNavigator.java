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

package com.tovo.eat.ui.payment;

public interface PaymentNavigator {

    void handleError(Throwable throwable);

    void goBack();


    void clickCard();

    void clickNetbanking();

    void clickUPI();

    void clickCOD();
    void showRetry();

    void clickwallet();

    void orderCompleted();

    void orderGenerated(Long orderId, String customerId, Integer amount);
    void pendingPayment(Long orderId, String customerId, Integer amount);

    void paymentSuccessed(boolean status);
    void postRegistration(Integer code);

    void showToast(String msg);

    void retryPaymentForSamePrderID();

}
