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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.utilities.CartRequestPojo;

import java.util.StringTokenizer;


/**
 * Created by amitshekhar on 07/07/17.
 */

public class MainViewModel extends BaseViewModel<MainNavigator> {

    public static final int NO_ACTION = -1, ACTION_ADD_ALL = 0, ACTION_DELETE_SINGLE = 1;
    public final ObservableBoolean cart = new ObservableBoolean();

    //private final MutableLiveData<List<QuestionCardData>> questionCardData;

    //private final ObservableList<QuestionCardData> questionDataList = new ObservableArrayList<>();
    private final ObservableField<String> appVersion = new ObservableField<>();
    private final ObservableField<String> userEmail = new ObservableField<>();
    private final ObservableField<String> userName = new ObservableField<>();
    private final ObservableField<String> userProfilePicUrl = new ObservableField<>();
    private final ObservableField<String> numOfCarts = new ObservableField<>();
    public final ObservableField<String> addressTitle = new ObservableField<>();

    public final ObservableField<String> toolbarTitle = new ObservableField<>();

    public final ObservableBoolean titleVisible=new ObservableBoolean();



    private int action = NO_ACTION;

    public MainViewModel(DataManager dataManager) {
        super(dataManager);
    }

   /* public EditAddressViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        questionCardData = new MutableLiveData<>();
        loadQuestionCards();
    }*/

    public int getAction() {
        return action;
    }



    public ObservableField<String> getAppVersion() {
        return appVersion;
    }

    /*public MutableLiveData<List<QuestionCardData>> getQuestionCardData() {
        return questionCardData;
    }*/

/*
    public ObservableList<QuestionCardData> getQuestionDataList() {
        return questionDataList;
    }
*/

/*
    public void setQuestionDataList(List<QuestionCardData> questionCardDatas) {
        action = ACTION_ADD_ALL;
        questionDataList.clear();
        questionDataList.addAll(questionCardDatas);
    }
*/


    public void selectAddress() {
        getNavigator().selectAddress();

    }

    public void gotoCart() {
        getNavigator().openCart();

    }





    public String updateAddressTitle(){

        return getDataManager().getCurrentAddressTitle();

    }




    public void gotoAccount() {
        getNavigator().openAccount();
    }

    public void gotoExplore() {
        getNavigator().openExplore();
    }


    public void gotoHome() {
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


    public void openDrawer() {
        getNavigator().openNavDrawer();
    }

    public ObservableField<String> getNumOfCarts() {
        return numOfCarts;
    }
}
