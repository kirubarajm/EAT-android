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
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.ui.filter.FilterRequestPojo;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.CartRequestPojo;
import com.tovo.eat.utilities.MasterPojo;
import com.tovo.eat.utilities.MvvmApp;


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
    public final ObservableBoolean isLiveOrder = new ObservableBoolean();
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

        if (cart.get()) {
            getNavigator().openCart();
        } else {

            getNavigator().toastMsg("No items in cart");
        }


    }


    public String updateAddressTitle() {

        return getDataManager().getCurrentAddressTitle();

    }


    public void trackLiveOrder() {

        getNavigator().trackLiveOrder(orderId);


    }





    public boolean isAddressAdded(){

        if (getDataManager().getCurrentAddressTitle()==null){

            return false;
        }else {

            return true;
        }

    }









    public void liveOrders() {

        if (!MvvmApp.getInstance().onCheckNetWork()) return;

        setIsLoading(true);
        GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, AppConstants.EAT_LIVE_ORDER_URL + getDataManager().getCurrentUserId(), LiveOrderResponsePojo.class, new Response.Listener<LiveOrderResponsePojo>() {
            @Override
            public void onResponse(LiveOrderResponsePojo response) {
                if (response != null) {
                    setIsLoading(false);

                    if (response.getResult().size() != 0) {

                        isLiveOrder.set(true);


                        kitchenImage.set(response.getResult().get(0).getMakeitimage());

                        if (response.getResult().get(0).getMakeitbrandname() != null) {
                            kitchenName.set(response.getResult().get(0).getMakeitbrandname());
                        } else {

                            kitchenName.set(response.getResult().get(0).getMakeitusername());
                        }

                        eta.set(response.getResult().get(0).getEta());

                        orderId = response.getResult().get(0).getOrderid();

                        getDataManager().setOrderId(orderId);


                    } else {
                        isLiveOrder.set(false);
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("", error.getMessage());
                setIsLoading(false);
            }
        });

        MvvmApp.getInstance().addToRequestQueue(gsonRequest);


    }


    public void gotoAccount() {
        getNavigator().openAccount();
    }

    public void gotoExplore() {
        getNavigator().openExplore();
    }


    public void gotoHome() {
        getDataManager().setIsFav(false);
        getNavigator().openHome();

    }

    public void totalCart() {

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
                    } else {
                        numOfCarts.set(String.valueOf(count));
                        cart.set(true);
                    }

                }

            }
        }
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
                Log.e("", error.getMessage());
                setIsLoading(false);
            }
        });

        MvvmApp.getInstance().addToRequestQueue(gsonRequest);


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


}
