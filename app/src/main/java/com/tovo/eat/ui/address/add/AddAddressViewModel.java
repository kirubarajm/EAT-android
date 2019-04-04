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

package com.tovo.eat.ui.address.add;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.text.Editable;
import android.text.TextWatcher;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;


/**
 * Created by amitshekhar on 07/07/17.
 */

public class AddAddressViewModel extends BaseViewModel<AddAddressNavigator> {


    public final ObservableBoolean cart = new ObservableBoolean();

    public final ObservableField<String> locationAddress = new ObservableField<>();
    public final ObservableField<String> area = new ObservableField<>();
    public final ObservableField<String> house = new ObservableField<>();

    public TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {



        }
    };


    public AddAddressViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void saveAddress(String locationAddress, String house, String area, String landmark) {

        if (locationAddress.equals("")) {

        }

        if (house.equals("")) {


        }

        if (area.equals("")) {

        }

        if (landmark.equals("")) {

        }

        if (!locationAddress.equals("") && !house.equals("") && !area.equals("") && !landmark.equals("")) {

            getNavigator().addressSaved();


        }else {

            getNavigator().emptyFields();

        }


    }
}
