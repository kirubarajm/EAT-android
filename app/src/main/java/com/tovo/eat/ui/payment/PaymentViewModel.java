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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.address.DefaultAddressRequest;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.ui.filter.FilterRequestPojo;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.CommonResponse;
import com.tovo.eat.utilities.MvvmApp;


/**
 * Created by amitshekhar on 07/07/17.
 */

public class PaymentViewModel extends BaseViewModel<PaymentNavigator> {


    public final ObservableBoolean cart = new ObservableBoolean();

    public final ObservableField<String> locationAddress = new ObservableField<>();
    public final ObservableField<String> area = new ObservableField<>();
    public final ObservableField<String> house = new ObservableField<>();
    public final ObservableField<String> landmark = new ObservableField<>();
    public final ObservableBoolean typeHome = new ObservableBoolean();
    public final ObservableBoolean typeOffice = new ObservableBoolean();
    public final ObservableBoolean typeOther = new ObservableBoolean();



    public PaymentViewModel(DataManager dataManager) {
        super(dataManager);
    }



    public void goBack(){
        getNavigator().goBack();
    }

}
