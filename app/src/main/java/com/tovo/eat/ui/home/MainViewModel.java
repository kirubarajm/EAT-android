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

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.ui.filter.FilterRequestPojo;
import com.tovo.eat.ui.signup.namegender.TokenRequest;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.CartRequestPojo;
import com.tovo.eat.utilities.CommonResponse;
import com.tovo.eat.utilities.MasterPojo;
import com.tovo.eat.utilities.MvvmApp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Created by amitshekhar on 07/07/17.
 */

public class MainViewModel extends BaseViewModel<MainNavigator> {

    public static final int NO_ACTION = -1, ACTION_ADD_ALL = 0, ACTION_DELETE_SINGLE = 1;
    public final ObservableBoolean cart = new ObservableBoolean();

    //private final MutableLiveData<List<QuestionCardData>> questionCardData;
    public final ObservableField<String> addressTitle = new ObservableField<>();
    public final ObservableField<String> toolbarTitle = new ObservableField<>();
    public final ObservableBoolean titleVisible = new ObservableBoolean();
    public final ObservableBoolean cartAvailable = new ObservableBoolean();
    public final ObservableField<String> kitchenName = new ObservableField<>();
    public final ObservableField<String> eta = new ObservableField<>();
    public final ObservableField<String> kitchenImage = new ObservableField<>();
    public final ObservableField<String> products = new ObservableField<>();
    public final ObservableBoolean isLiveOrder = new ObservableBoolean();
    public final ObservableBoolean isHome = new ObservableBoolean();
    public final ObservableBoolean isExplore = new ObservableBoolean();
    public final ObservableBoolean isCart = new ObservableBoolean();
    public final ObservableBoolean isMyAccount = new ObservableBoolean();
    //private final ObservableList<QuestionCardData> questionDataList = new ObservableArrayList<>();
    private final ObservableField<String> appVersion = new ObservableField<>();
    private final ObservableField<String> userEmail = new ObservableField<>();
    private final ObservableField<String> userName = new ObservableField<>();
    private final ObservableField<String> userProfilePicUrl = new ObservableField<>();
    private final ObservableField<String> numOfCarts = new ObservableField<>();
    private int orderId;


    private int action = NO_ACTION;

    public MainViewModel(DataManager dataManager) {
        super(dataManager);

        getDataManager().setIsFav(false);

        masterRequest();


        /*getDataManager().setisPasswordStatus(false);*/
    }


    public int getAction() {
        return action;
    }


    public ObservableField<String> getAppVersion() {
        return appVersion;
    }


    public void selectAddress() {
        getNavigator().selectAddress();

    }

    public void gotoCart() {

        /*if (getDataManager().getCartDetails() != null) {*/
        if (!isCart.get()) {

            getNavigator().openCart();

            isHome.set(false);
            isExplore.set(false);
            isCart.set(true);
            isMyAccount.set(false);
        }

     /*   } else {

            getNavigator().toastMsg("No items in cart");
        }*/


    }


    public String updateAddressTitle() {

        return getDataManager().getCurrentAddressTitle();

    }


    public void trackLiveOrder() {

        getNavigator().trackLiveOrder(orderId);


    }


    public boolean isAddressAdded() {

        if (getDataManager().getCurrentLat() == null) {

            return false;
        } else {

            return true;
        }

    }


    public void liveOrders() {

        if (!MvvmApp.getInstance().onCheckNetWork()) return;

        try {


            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, AppConstants.EAT_LIVE_ORDER_URL + getDataManager().getCurrentUserId(), LiveOrderResponsePojo.class, new Response.Listener<LiveOrderResponsePojo>() {
                @Override
                public void onResponse(LiveOrderResponsePojo response) {
                    if (response != null) {
                        setIsLoading(false);

                        if (response.getStatus()) {

                            if (response.getResult().size() != 0) {

                                isLiveOrder.set(true);

                                kitchenImage.set(response.getResult().get(0).getMakeitimage());

                                if (response.getResult().get(0).getMakeitbrandname().isEmpty()) {
                                    kitchenName.set(response.getResult().get(0).getMakeitusername());
                                } else {

                                    kitchenName.set(response.getResult().get(0).getMakeitbrandname());
                                }
                                // 2019-05-09T13:21:54.000Z
                                try {
                                    String strDate = response.getResult().get(0).getDeliverytime();
                                    DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                                    DateFormat currentFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    String outputDateStr = "";
                                    //Date  date1 = new Date(strDate);
                                    Date date = currentFormat.parse(strDate);
                                    outputDateStr = dateFormat.format(date);
                                    eta.set(outputDateStr);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            /*String startTime = response.getResult().get(0).getDeliverytime();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
                            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                            SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm a");
                            Date dt;
                            try {
                                dt = sdf.parse(startTime);

                                String s=sdfs.format(dt);

                                eta.set(s);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }*/


                                // eta.set(response.getResult().get(0).getDeliverytime());

                                orderId = response.getResult().get(0).getOrderid();

                                getDataManager().setOrderId(orderId);

                                StringBuilder itemsBuilder = new StringBuilder();

                                for (int i = 0; i < response.getResult().get(0).getItems().size(); i++) {
                                    itemsBuilder.append(response.getResult().get(0).getItems().get(i).getProductName());

                                    if (response.getResult().get(0).getItems().size() - 1 != i) {
                                        itemsBuilder.append(" , ");
                                    }
                                }

                                String item = itemsBuilder.toString();
                                products.set(item);


                            } else {
                                isLiveOrder.set(false);
                            }

                        } else {
                            isLiveOrder.set(false);
                        }

                        if (response.getOrderdetails().size() > 0) {

                            boolean st = response.getOrderdetails().get(0).getRating();

                            if (!st) {

                                if (getDataManager().getRatingAppStatus()) {

                                    if (response.getOrderdetails().get(0).getShowRating()) {

                                        if (response.getOrderdetails().get(0).getOrderid().equals(getDataManager().getRatingOrderid())) {
                                            if (getDataManager().getRatingSkips() < 3) {
                                                getNavigator().showOrderRating(response.getOrderdetails().get(0).getOrderid(), response.getOrderdetails().get(0).getBrandname());
                                            }

                                        } else {
                                            getDataManager().saveRatingSkipDate(0);
                                            getDataManager().saveRatingOrderId(response.getOrderdetails().get(0).getOrderid());
                                            getNavigator().showOrderRating(response.getOrderdetails().get(0).getOrderid(), response.getOrderdetails().get(0).getBrandname());

                                        }

                                    }


                                }
                            }
                        }

                    } else {
                        isLiveOrder.set(false);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Log.e("", error.getMessage());
                    setIsLoading(false);
                    isLiveOrder.set(false);
                }
            }, AppConstants.API_VERSION_ONE);

            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (Exception ee) {

            ee.printStackTrace();

        }
    }

    public void saveToken(String token) {
        int userIdMain = getDataManager().getCurrentUserId();
        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        setIsLoading(true);
        try {

            GsonRequest gsonRequest = new GsonRequest(Request.Method.PUT, AppConstants.EAT_FCM_TOKEN_URL, CommonResponse.class, new TokenRequest(userIdMain, token), new Response.Listener<CommonResponse>() {
                @Override
                public void onResponse(CommonResponse response) {
                    if (response != null) {

                        if (response.isStatus()) {


                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setIsLoading(false);
                }
            }, AppConstants.API_VERSION_ONE);
            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (Exception ee) {

            ee.printStackTrace();

        }
    }

    public void gotoAccount() {

        if (!isMyAccount.get()) {
            getNavigator().openAccount();
            isHome.set(false);
            isExplore.set(false);
            isCart.set(false);
            isMyAccount.set(true);
        }


    }

    public void gotoExplore() {

        if (!isExplore.get()) {

            getNavigator().openExplore();
            isHome.set(false);
            isExplore.set(true);
            isCart.set(false);
            isMyAccount.set(false);

        }
    }

    public void gotoHome() {


        if (!isHome.get()) {
            getDataManager().setIsFav(false);
            getNavigator().openHome();
            isHome.set(true);
            isExplore.set(false);
            isCart.set(false);
            isMyAccount.set(false);

        }
    }

    public boolean totalCart() {
        try {
            Gson sGson = new GsonBuilder().create();
            CartRequestPojo cartRequestPojo = sGson.fromJson(getDataManager().getCartDetails(), CartRequestPojo.class);
            cart.set(false);
            if (cartRequestPojo == null)
                cartRequestPojo = new CartRequestPojo();
            int count = 0;

            if (cartRequestPojo.getCartitems() != null) {
                if (cartRequestPojo.getCartitems().size() == 0) {
                    cart.set(false);
                } else {

                    for (int i = 0; i < cartRequestPojo.getCartitems().size(); i++) {

                        count = count + cartRequestPojo.getCartitems().get(i).getQuantity();

                        if (count == 0) {
                            cart.set(false);
                            return false;

                        } else {
                            numOfCarts.set(String.valueOf(count));
                            cart.set(true);
                        }

                    }

                }
            }
        } catch (Exception ee) {

            ee.printStackTrace();

        }
        return false;
    }

    public ObservableField<String> getUserEmail() {
        return userEmail;
    }

    public ObservableField<String> getUserName() {
        return userName;
    }

    public ObservableField<String> getUserProfilePicUrl() {
        return userProfilePicUrl;
    }

/*
    public void loadQuestionCards() {
        getCompositeDisposable().add(getDataManager()
                .getQuestionCardData()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(questionList -> {
                    if (questionList != null) {
                        action = ACTION_ADD_ALL;
                        questionCardData.setValue(questionList);
                    }
                }, throwable -> {

                }));
    }
*/

    public void logout() {
        getDataManager().setUserAsLoggedOut();
    }

    public void onNavMenuCreated() {
        final String currentUserName = getDataManager().getCurrentUserName();
        if (!TextUtils.isEmpty(currentUserName)) {
            userName.set(currentUserName);
        }

        final String currentUserEmail = getDataManager().getCurrentUserEmail();
        if (!TextUtils.isEmpty(currentUserEmail)) {
            userEmail.set(currentUserEmail);
        }
/*
        final String profilePicUrl = getDataManager().getCurrentUserProfilePicUrl();
        if (!TextUtils.isEmpty(profilePicUrl)) {
            userProfilePicUrl.set(profilePicUrl);
        }*/
    }

/*
    public void removeQuestionCard() {
        action = ACTION_DELETE_SINGLE;
        questionDataList.remove(0);
        questionCardData.getValue().remove(0);
    }
*/

    public void updateAppVersion(String version) {
        appVersion.set(version);
    }


    public ObservableField<String> getNumOfCarts() {
        return numOfCarts;
    }


    public void masterRequest() {
        try {

            GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, AppConstants.EAT_MASTER, MasterPojo.class, new Response.Listener<MasterPojo>() {
                @Override
                public void onResponse(MasterPojo response) {
                    if (response != null) {

                        Gson gson = new Gson();
                        String master = gson.toJson(response);
                        getDataManager().saveMaster("");
                        getDataManager().saveMaster(master);

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //  Log.e("", error.getMessage());
                    setIsLoading(false);
                }
            }, AppConstants.API_VERSION_ONE);

            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (Exception ee) {

            ee.printStackTrace();

        }

    }

    public boolean checkInternet() {
        return MvvmApp.getInstance().onCheckNetWork();

    }

    public void saveRequestData() {


        FilterRequestPojo filterRequestPojo;


        if (getDataManager().getFilterSort() != null) {

            Gson sGson = new GsonBuilder().create();
            filterRequestPojo = sGson.fromJson(getDataManager().getFilterSort(), FilterRequestPojo.class);

            filterRequestPojo.setEatuserid(getDataManager().getCurrentUserId());
            filterRequestPojo.setLat(getDataManager().getCurrentLat());
            filterRequestPojo.setLon(getDataManager().getCurrentLng());

            Gson gson = new Gson();
            String json = gson.toJson(filterRequestPojo);
            getDataManager().setFilterSort(json);
        } else {
            filterRequestPojo = new FilterRequestPojo();

            filterRequestPojo.setEatuserid(getDataManager().getCurrentUserId());
            filterRequestPojo.setLat(getDataManager().getCurrentLat());
            filterRequestPojo.setLon(getDataManager().getCurrentLng());

            Gson gson = new Gson();
            String json = gson.toJson(filterRequestPojo);
            getDataManager().setFilterSort(json);
        }

    }


    public void currentLatLng(double lat, double lng) {

        if (getDataManager().getCurrentLat() == null) {
            getDataManager().setCurrentAddressTitle("Current location");
            getDataManager().setCurrentLat(lat);
            getDataManager().setCurrentLng(lng);
            getNavigator().disConnectGPS();
            getNavigator().openHome();
        }

    }


}
