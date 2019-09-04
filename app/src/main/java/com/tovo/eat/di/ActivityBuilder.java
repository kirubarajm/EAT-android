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
import com.tovo.eat.ui.account.edit.EditAccountActivity;
import com.tovo.eat.ui.account.edit.EditAccountModule;
import com.tovo.eat.ui.account.favorites.FavouritesActivity;
import com.tovo.eat.ui.account.favorites.FavouritesModule;
import com.tovo.eat.ui.account.favorites.favdish.FavoritesDishProvider;
import com.tovo.eat.ui.account.favorites.favkitchen.FavoritesKitchenProvider;
import com.tovo.eat.ui.account.favorites.tab.FavoritesTabActivity;
import com.tovo.eat.ui.account.favorites.tab.FavoritesTabActivityModule;
import com.tovo.eat.ui.account.favorites.tab.FavoritesTabActivityProvider;
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
import com.tovo.eat.ui.address.select.SelectAddressListActivity;
import com.tovo.eat.ui.alerts.ordercanceled.OrderCanceledProvider;
import com.tovo.eat.ui.cart.CartProvider;
import com.tovo.eat.ui.cart.coupon.CouponListActivity;
import com.tovo.eat.ui.cart.coupon.CouponListModule;
import com.tovo.eat.ui.cart.refund.RefundListActivity;
import com.tovo.eat.ui.cart.refund.RefundListModule;
import com.tovo.eat.ui.cart.refund.alert.DialogRefundAlertProvider;
import com.tovo.eat.ui.filter.FilterProvider;
import com.tovo.eat.ui.forgotpassword.ForgotPasswordActivity;
import com.tovo.eat.ui.forgotpassword.ForgotPasswordActivityModule;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.home.MainActivityModule;
import com.tovo.eat.ui.home.dialog.DialogSelectAddressProvider;
import com.tovo.eat.ui.home.homemenu.HomeTabProvider;
import com.tovo.eat.ui.home.homemenu.dish.DishProvider;
import com.tovo.eat.ui.home.homemenu.dish.dialog.DialogChangeKitchenProvider;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenProvider;
import com.tovo.eat.ui.home.homemenu.story.storiesactivity.StoriesTabActivity;
import com.tovo.eat.ui.home.homemenu.story.storiesactivity.StoriesTabActivityModule;
import com.tovo.eat.ui.home.homemenu.story.storiesactivity.fragment.StoriesViewPagerProvider;

import com.tovo.eat.ui.home.kitchendish.KitchenDishActivity;
import com.tovo.eat.ui.home.kitchendish.KitchenDishModule;
import com.tovo.eat.ui.home.kitchendish.dialog.DialogChangeKitchenDishProvider;
import com.tovo.eat.ui.home.region.list.RegionDetailsActivity;
import com.tovo.eat.ui.home.region.list.RegionDetailsModule;
import com.tovo.eat.ui.home.region.viewmore.RegionListActivity;
import com.tovo.eat.ui.home.region.viewmore.RegionListModule;
import com.tovo.eat.ui.kitchendetails.KitchenDetailsActivity;
import com.tovo.eat.ui.kitchendetails.KitchenDetailsModule;
import com.tovo.eat.ui.kitchendetails.dialog.DialogChangeKitchenDetailsProvider;
import com.tovo.eat.ui.kitchendetails.viewimage.ViewImageActivity;
import com.tovo.eat.ui.kitchendetails.viewimage.ViewImageModule;
import com.tovo.eat.ui.onboarding.OnBoardingActivity;
import com.tovo.eat.ui.onboarding.OnBoardingActivityModule;
import com.tovo.eat.ui.orderplaced.OrderPlacedActivity;
import com.tovo.eat.ui.orderplaced.OrderPlacedModule;
import com.tovo.eat.ui.orderrating.OrderRatingProvider;
import com.tovo.eat.ui.payment.PaymentActivity;
import com.tovo.eat.ui.payment.PaymentModule;
import com.tovo.eat.ui.payment.pendingpaymentpage.PendingPaymentPageProvider;
import com.tovo.eat.ui.pendingpayment.PendingPaymentProvider;
import com.tovo.eat.ui.registration.RegistrationActivity;
import com.tovo.eat.ui.registration.RegistrationActivityModule;
import com.tovo.eat.ui.search.SearchProvider;
import com.tovo.eat.ui.search.dish.SearchDishActivity;
import com.tovo.eat.ui.search.dish.SearchDishModule;
import com.tovo.eat.ui.signup.SignUpActivity;
import com.tovo.eat.ui.signup.SignUpActivityModule;
import com.tovo.eat.ui.signup.fagsandsupport.FaqsAndSupportActivity;
import com.tovo.eat.ui.signup.fagsandsupport.FaqsAndSupportModule;
import com.tovo.eat.ui.signup.faqs.FaqActivity;
import com.tovo.eat.ui.signup.faqs.FaqFragmentModule;
import com.tovo.eat.ui.signup.namegender.NameGenderActivity;
import com.tovo.eat.ui.signup.namegender.NameGenderActivityModule;
import com.tovo.eat.ui.signup.opt.OtpActivity;
import com.tovo.eat.ui.signup.opt.OtpActivityModule;
import com.tovo.eat.ui.signup.privacy.PrivacyActivity;
import com.tovo.eat.ui.signup.privacy.PrivacyModule;
import com.tovo.eat.ui.signup.tandc.TermsAndConditionActivity;
import com.tovo.eat.ui.signup.tandc.TermsAndConditionModule;
import com.tovo.eat.ui.splash.SplashActivity;
import com.tovo.eat.ui.splash.SplashActivityModule;
import com.tovo.eat.ui.track.OrderTrackingActivity;
import com.tovo.eat.ui.track.OrderTrackingModule;
import com.tovo.eat.ui.track.help.OrderHelpActivity;
import com.tovo.eat.ui.track.help.OrderHelpModule;
import com.tovo.eat.ui.track.orderdetails.OrderDetailsActivity;
import com.tovo.eat.ui.track.orderdetails.OrderDetailsModule;
import com.tovo.eat.ui.update.UpdateActivity;
import com.tovo.eat.ui.update.UpdateModule;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;
import com.tovo.eat.utilities.nointernet.InternetErrorModule;

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
            CartProvider.class,
            FilterProvider.class,
            OrderRatingProvider.class,
            OrderCanceledProvider.class,
            DialogSelectAddressProvider.class,
            DialogChangeKitchenProvider.class,
            DialogRefundAlertProvider.class,
            PendingPaymentProvider.class,
            SearchProvider.class

    })
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = {KitchenDishModule.class, DialogChangeKitchenDishProvider.class,OrderCanceledProvider.class})
    abstract KitchenDishActivity bindKitchenDishActivity();


    @ContributesAndroidInjector(modules = {KitchenDetailsModule.class, DialogChangeKitchenDetailsProvider.class,OrderCanceledProvider.class})
    abstract KitchenDetailsActivity bindKitchenDetailsActivity();

    @ContributesAndroidInjector(modules = {FavoritesTabActivityModule.class, FavoritesDishProvider.class,
            FavoritesKitchenProvider.class, DialogChangeKitchenProvider.class,OrderCanceledProvider.class})
    abstract FavoritesTabActivity bindFavoritesTabActivity();


    @ContributesAndroidInjector(modules = {AddAddressModule.class,OrderCanceledProvider.class})
    abstract AddAddressActivity bindAddAddressActivity();


    @ContributesAndroidInjector(modules = {ViewImageModule.class,OrderCanceledProvider.class})
    abstract ViewImageActivity bindViewImageActivity();


    @ContributesAndroidInjector(modules = {SearchDishModule.class, DialogChangeKitchenDishProvider.class,OrderCanceledProvider.class})
    abstract SearchDishActivity bindSearchDishActivity();


    @ContributesAndroidInjector(modules = {RegionDetailsModule.class,OrderCanceledProvider.class})
    abstract RegionDetailsActivity bindRegionDetailsActivity();


    @ContributesAndroidInjector(modules = {FaqsAndSupportModule.class,OrderCanceledProvider.class})
    abstract FaqsAndSupportActivity bindFaqsAndSupportActivity();


    @ContributesAndroidInjector(modules ={ RegionListModule.class,OrderCanceledProvider.class})
    abstract RegionListActivity bindRegionListActivity();


    @ContributesAndroidInjector(modules = {OrderDetailsModule.class,OrderCanceledProvider.class})
    abstract OrderDetailsActivity bindOrderDetailsActivity();


    @ContributesAndroidInjector(modules ={ OrderHelpModule.class,OrderCanceledProvider.class})
    abstract OrderHelpActivity bindOrderHelpActivity();


    @ContributesAndroidInjector(modules = {PaymentModule.class,OrderCanceledProvider.class, PendingPaymentPageProvider.class,PendingPaymentProvider.class})
    abstract PaymentActivity bindPaymentActivity();


    @ContributesAndroidInjector(modules = {FavouritesModule.class, FavoritesDishProvider.class, FavoritesKitchenProvider.class,
            DialogChangeKitchenProvider.class,OrderCanceledProvider.class})
    abstract FavouritesActivity bindFavouritesActivity();


    @ContributesAndroidInjector(modules = {EditAddressModule.class,OrderCanceledProvider.class})
    abstract EditAddressActivity bindEditAddressActivity();

    @ContributesAndroidInjector(modules ={ PrivacyModule.class,OrderCanceledProvider.class})
    abstract PrivacyActivity bindPrivacyActivity();


    @ContributesAndroidInjector(modules ={ AddressListModule.class,OrderCanceledProvider.class})
    abstract AddressListActivity bindAddressListActivity();


    @ContributesAndroidInjector(modules = {RefundListModule.class,OrderCanceledProvider.class})
    abstract RefundListActivity bindRefundListActivity();

    @ContributesAndroidInjector(modules ={ TermsAndConditionModule.class,OrderCanceledProvider.class})
    abstract TermsAndConditionActivity bindTermsAndConditionActivity();


    @ContributesAndroidInjector(modules = {FaqFragmentModule.class,OrderCanceledProvider.class})
    abstract FaqActivity bindFaqActivity();


    @ContributesAndroidInjector(modules ={ SelectAddressListModule.class,OrderCanceledProvider.class})
    abstract SelectAddressListActivity bindSelectSelectAddressListActivity();

    @ContributesAndroidInjector(modules = {FeedbackAndSupportActivityModule.class,OrderCanceledProvider.class})
    abstract FeedbackAndSupportActivity bindFeedbackSupportActivity();

    @ContributesAndroidInjector(modules = {SupportActivityModule.class,OrderCanceledProvider.class})
    abstract SupportActivity bindQueriesActivity();

    @ContributesAndroidInjector(modules = {RepliesActivityModule.class,OrderCanceledProvider.class})
    abstract RepliesActivity bindRepliesActivity();

    @ContributesAndroidInjector(modules = {ChatActivityModule.class,OrderCanceledProvider.class})
    abstract ChatActivity bindChatActivity();

    @ContributesAndroidInjector(modules = {FeedbackActivityModule.class,OrderCanceledProvider.class})
    abstract FeedbackActivity bindFeedbackActivity();

    @ContributesAndroidInjector(modules = {ReferralsActivityModule.class,OrderCanceledProvider.class})
    abstract ReferralsActivity bindReferralskActivity();

    @ContributesAndroidInjector(modules = {OrderHistoryActivityModule.class,OrderCanceledProvider.class})
    abstract OrderHistoryActivity bindOrdersHistoryActivity();

    @ContributesAndroidInjector(modules = {OrderHistoryActivityViewModule.class,OrderCanceledProvider.class})
    abstract OrderHistoryActivityView bindOrdersHistoryViewActivity();

    @ContributesAndroidInjector(modules = {OrderTrackingModule.class,OrderCanceledProvider.class})
    abstract OrderTrackingActivity bindOrderTrackingActivity();

    @ContributesAndroidInjector(modules = {SignUpActivityModule.class,OrderCanceledProvider.class})
    abstract SignUpActivity bindSignUpActivity();

    @ContributesAndroidInjector(modules = OtpActivityModule.class)
    abstract OtpActivity bindOtpActivity();

    @ContributesAndroidInjector(modules = NameGenderActivityModule.class)
    abstract NameGenderActivity bindNameGenderActivity();

    @ContributesAndroidInjector(modules = RegistrationActivityModule.class)
    abstract RegistrationActivity bindRegistrationActivity();
/*
    @ContributesAndroidInjector(modules = OrderCanceledModule.class)
    abstract OrderCanceledBottomFragment bindorderRatingActivity();*/

    @ContributesAndroidInjector(modules = SplashActivityModule.class)
    abstract SplashActivity bindSplashActivity();

    @ContributesAndroidInjector(modules = ForgotPasswordActivityModule.class)
    abstract ForgotPasswordActivity bindForgotPasswordActivity();


    @ContributesAndroidInjector(modules = OrderPlacedModule.class)
    abstract OrderPlacedActivity bindOrderPlacedActivity();

    @ContributesAndroidInjector(modules = OnBoardingActivityModule.class)
    abstract OnBoardingActivity bindOnBoardingActivity();

    @ContributesAndroidInjector(modules = {EditAccountModule.class,OrderCanceledProvider.class})
    abstract EditAccountActivity bindEditAccountActivity();

    @ContributesAndroidInjector(modules = {StoriesTabActivityModule.class, StoriesViewPagerProvider.class})
    abstract StoriesTabActivity bindSampleActivity();


  @ContributesAndroidInjector(modules = {CouponListModule.class,OrderCanceledProvider.class})
    abstract CouponListActivity bindCouponListActivity();

@ContributesAndroidInjector(modules = {UpdateModule.class,OrderCanceledProvider.class})
    abstract UpdateActivity bindUpdateActivity();
@ContributesAndroidInjector(modules = {InternetErrorModule.class})
    abstract InternetErrorFragment bindInternetActivity();

}
