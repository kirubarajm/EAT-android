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

package com.tovo.eat.di;

import com.tovo.eat.ui.address.add.AddAddressActivity;
import com.tovo.eat.ui.address.add.AddAddressModule;
import com.tovo.eat.ui.address.edit.EditAddressActivity;
import com.tovo.eat.ui.address.edit.EditAddressModule;
import com.tovo.eat.ui.cart.CartActivity;
import com.tovo.eat.ui.cart.CartModule;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.home.MainActivityModule;
import com.tovo.eat.ui.home.homemenu.HomeTabProvider;
import com.tovo.eat.ui.home.homemenu.dish.DishProvider;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenProvider;
import com.tovo.eat.ui.home.kitchendish.KitchenDishModule;
import com.tovo.eat.ui.home.kitchendish.KitchenDishActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by amitshekhar on 14/09/17.
 */
@Module
public abstract class ActivityBuilder {


    @ContributesAndroidInjector(modules = {
            MainActivityModule.class,
            HomeTabProvider.class,
            KitchenProvider.class,
            DishProvider.class,

    })
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = KitchenDishModule.class)
    abstract KitchenDishActivity bindKitchenDishActivity();

    @ContributesAndroidInjector(modules = CartModule.class)
    abstract CartActivity bindCartActivity();

    @ContributesAndroidInjector(modules = AddAddressModule.class)
    abstract AddAddressActivity bindAddAddressActivity();

    @ContributesAndroidInjector(modules = EditAddressModule.class)
    abstract EditAddressActivity bindEditAddressActivity();
}
