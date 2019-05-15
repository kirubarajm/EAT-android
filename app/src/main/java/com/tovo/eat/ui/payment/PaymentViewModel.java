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

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;


/**
 * Created by amitshekhar on 07/07/17.
 */

public class PaymentViewModel extends BaseViewModel<PaymentNavigator> {


    public final ObservableBoolean cart = new ObservableBoolean();

    public final ObservableField<String> locationAddress = new ObservableField<>();
    public final ObservableField<String> area = new ObservableField<>();
    public final ObservableField<String> house = new ObservableField<>();
    public final ObservableField<String> landmark = new ObservableField<>();
    public final ObservableBoolean cardClicked = new ObservableBoolean();
    public final ObservableBoolean netbankingClicked = new ObservableBoolean();
    public final ObservableBoolean upiClicked = new ObservableBoolean();
    public final ObservableBoolean codClicked = new ObservableBoolean();
    public final ObservableBoolean walletClicked = new ObservableBoolean();


    public PaymentViewModel(DataManager dataManager) {
        super(dataManager);
    }


    public void goBack() {
        getNavigator().goBack();
    }

    public void card() {
        getNavigator().clickCard();


        cardClicked.set(true);
        netbankingClicked.set(false);
        upiClicked.set(false);
        codClicked.set(false);
        walletClicked.set(false);


    }

    public void netbanking() {
        getNavigator().clickNetbanking();
        cardClicked.set(false);
        netbankingClicked.set(true);
        upiClicked.set(false);
        codClicked.set(false);
        walletClicked.set(false);
    }

    public void upi() {
        getNavigator().clickUPI();
        cardClicked.set(false);
        netbankingClicked.set(false);
        upiClicked.set(true);
        codClicked.set(false);
        walletClicked.set(false);
    }

    public void cod() {
        getNavigator().clickCOD();
        cardClicked.set(false);
        netbankingClicked.set(false);
        upiClicked.set(false);
        codClicked.set(true);
        walletClicked.set(false);

    }
    public void wallet() {
        getNavigator().clickCOD();
        cardClicked.set(false);
        netbankingClicked.set(false);
        upiClicked.set(false);
        codClicked.set(false);
        walletClicked.set(true);

    }

}
