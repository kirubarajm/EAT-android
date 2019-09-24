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

package com.tovo.eat.ui.cart;

import android.support.v7.widget.LinearLayoutManager;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.cart.refund.RefundListAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class CartModule {


    DataManager dataManager;

    @Provides
    CartViewModel provideMainViewModel(DataManager dataManager) {

        this.dataManager = dataManager;

        return new CartViewModel(dataManager);
    }

    @Provides
    CartDishAdapter provideCartDishAdapter() {
        return new CartDishAdapter(new ArrayList<>(), dataManager);
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(CartActivity activity) {
        return new LinearLayoutManager(activity.getContext());
    }

    @Provides
    RefundListAdapter provideRefundsListAdapter() {
        return new RefundListAdapter(new ArrayList<>(), dataManager);
    }

    @Provides
    BillListAdapter provideBillListAdapter() {
        return new BillListAdapter(new ArrayList<>());
    }

}
