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

package com.tovo.eat.ui.address.edit;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;


/**
 * Created by amitshekhar on 07/07/17.
 */

public class EditAddressViewModel extends BaseViewModel<EditAddressNavigator> {


    public final ObservableBoolean cart = new ObservableBoolean();

    private final ObservableField<String> numOfCarts = new ObservableField<>();


    public EditAddressViewModel(DataManager dataManager) {
        super(dataManager);
    }



}
