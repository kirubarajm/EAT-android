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

import com.tovo.eat.ui.account.MyAccountProvider;
import com.tovo.eat.ui.account.favorites.FavoritesTabActivity;
import com.tovo.eat.ui.account.favorites.FavoritesTabActivityModule;
import com.tovo.eat.ui.account.favorites.FavoritesTabActivityProvider;
import com.tovo.eat.ui.account.favorites.favdish.FavoritesDishProvider;
import com.tovo.eat.ui.account.favorites.favkitchen.FavoritesKitchenProvider;
import com.tovo.eat.ui.account.feedbackandsupport.FeedbackAndSupportActivity;
import com.tovo.eat.ui.account.feedbackandsupport.FeedbackAndSupportActivityModule;
import com.tovo.eat.ui.account.feedbackandsupport.feedback.FeedbackActivity;
import com.tovo.eat.ui.account.feedbackandsupport.feedback.FeedbackActivityModule;
import com.tovo.eat.ui.account.feedbackandsupport.support.SupportActivity;
import com.tovo.eat.ui.account.feedbackandsupport.support.SupportActivityModule;
import com.tovo.eat.ui.account.feedbackandsupport.support.replies.RepliesActivity;
import com.tovo.eat.ui.account.feedbackandsupport.support.replies.RepliesActivityModule;
import com.tovo.eat.ui.account.feedbackandsupport.support.replies.chat.ChatActivity;
import com.tovo.eat.ui.account.feedbackandsupport.support.replies.chat.ChatActivityModule;
import com.tovo.eat.ui.account.orderhistory.historylist.OrderHistoryActivity;
import com.tovo.eat.ui.account.orderhistory.historylist.OrderHistoryActivityModule;
import com.tovo.eat.ui.account.orderhistory.ordersview.OrderHistoryActivityView;
import com.tovo.eat.ui.account.orderhistory.ordersview.OrderHistoryActivityViewModule;
import com.tovo.eat.ui.account.referrals.ReferralsActivity;
import com.tovo.eat.ui.account.referrals.ReferralsActivityModule;
import com.tovo.eat.ui.address.add.AddAddressActivity;
import com.tovo.eat.ui.address.add.AddAddressModule;
import com.tovo.eat.ui.address.edit.EditAddressActivity;
import com.tovo.eat.ui.address.edit.EditAddressModule;
import com.tovo.eat.ui.address.list.AddressListActivity;
import com.tovo.eat.ui.address.list.AddressListModule;
import com.tovo.eat.ui.address.select.SelectAddressListModule;
import com.tovo.eat.ui.address.select.SelectSelectAddressListActivity;
import com.tovo.eat.ui.cart.CartProvider;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.home.MainActivityModule;
import com.tovo.eat.ui.home.homemenu.HomeTabProvider;
import com.tovo.eat.ui.home.homemenu.dish.DishProvider;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenProvider;
import com.tovo.eat.ui.home.kitchendish.KitchenDishActivity;
import com.tovo.eat.ui.home.kitchendish.KitchenDishModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class ActivityBuilder {


    @ContributesAndroidInjector(modules = {
            MainActivityModule.class,
            HomeTabProvider.class,
            KitchenProvider.class,
            DishProvider.class,
            MyAccountProvider.class,
            FavoritesTabActivityProvider.class,
            CartProvider.class

    })
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = KitchenDishModule.class)
    abstract KitchenDishActivity bindKitchenDishActivity();


    @ContributesAndroidInjector(modules = AddAddressModule.class)
    abstract AddAddressActivity bindAddAddressActivity();

    @ContributesAndroidInjector(modules = EditAddressModule.class)
    abstract EditAddressActivity bindEditAddressActivity();


    @ContributesAndroidInjector(modules = AddressListModule.class)
    abstract AddressListActivity bindAddressListActivity();


    @ContributesAndroidInjector(modules = SelectAddressListModule.class)
    abstract SelectSelectAddressListActivity bindSelectSelectAddressListActivity();

    @ContributesAndroidInjector(modules = {FavoritesTabActivityModule.class, FavoritesDishProvider.class,
            FavoritesKitchenProvider.class,})
    abstract FavoritesTabActivity bindFavListActivity();

    @ContributesAndroidInjector(modules = {FeedbackAndSupportActivityModule.class})
    abstract FeedbackAndSupportActivity bindFeedbackSupportActivity();

    @ContributesAndroidInjector(modules = {SupportActivityModule.class})
    abstract SupportActivity bindQueriesActivity();

    @ContributesAndroidInjector(modules = {RepliesActivityModule.class})
    abstract RepliesActivity bindRepliesActivity();

    @ContributesAndroidInjector(modules = {ChatActivityModule.class})
    abstract ChatActivity bindChatActivity();

    @ContributesAndroidInjector(modules = {FeedbackActivityModule.class})
    abstract FeedbackActivity bindFeedbackActivity();

   @ContributesAndroidInjector(modules = {ReferralsActivityModule.class})
    abstract ReferralsActivity bindReferralskActivity();

   @ContributesAndroidInjector(modules = {OrderHistoryActivityModule.class})
    abstract OrderHistoryActivity bindOrdersHistoryActivity();

  @ContributesAndroidInjector(modules = {OrderHistoryActivityViewModule.class})
    abstract OrderHistoryActivityView bindOrdersHistoryViewActivity();


}
