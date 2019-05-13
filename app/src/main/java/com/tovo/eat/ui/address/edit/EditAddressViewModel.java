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
import com.tovo.eat.ui.address.add.AddressRequestPojo;
import com.tovo.eat.ui.address.add.AddressResponse;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.ui.filter.FilterRequestPojo;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.CommonResponse;
import com.tovo.eat.utilities.MvvmApp;


/**
 * Created by amitshekhar on 07/07/17.
 */

public class EditAddressViewModel extends BaseViewModel<EditAddressNavigator> {


    public final ObservableBoolean cart = new ObservableBoolean();

    public final ObservableField<String> locationAddress = new ObservableField<>();
    public final ObservableField<String> area = new ObservableField<>();
    public final ObservableField<String> house = new ObservableField<>();
    public final ObservableField<String> landmark = new ObservableField<>();
    public final ObservableField<String> title = new ObservableField<>();


    public final ObservableBoolean typeHome = new ObservableBoolean();
    public final ObservableBoolean typeOffice = new ObservableBoolean();
    public final ObservableBoolean typeOther = new ObservableBoolean();


    public Integer mAid;


    public void goBack(){


        getNavigator().goBack();

    }



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
    AddressRequestPojo request = new AddressRequestPojo();


    public EditAddressViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void locateMe() {

        getNavigator().myLocationn();

    }


    public void clickHome() {


        if (typeHome.get()) {

            typeHome.set(false);

        } else {
            typeHome.set(true);
            typeOffice.set(false);
            typeOther.set(false);
        }

    }

    public void clickOffice() {


        if (typeOffice.get()) {

            typeOffice.set(false);

        } else {


            typeHome.set(false);
            typeOffice.set(true);
            typeOther.set(false);


        }

    }


    public void clickOther() {


        if (typeOther.get()) {

            typeOther.set(false);

        } else {

            typeHome.set(false);
            typeOffice.set(false);
            typeOther.set(true);
        }

    }


    public void saveAddress(String lat, String lng, String pincode, Integer aid) {

        request.setLat(lat);
        request.setLon(lng);
        request.setPincode(pincode);
        mAid=aid;

        if (aid!=null&&aid!=0){
            request.setAid(aid);

        }


    }


    public void saveAddress(String locationAddress, String house, String area, String landmark, String title) {

        if (locationAddress.equals("")) {

        }

        if (house.equals("")) {


        }

        if (area.equals("")) {

        }

        if (landmark.equals("")) {

        }

        if (!locationAddress.equals("") && !house.equals("") && !area.equals("") && !landmark.equals("")) {


            if (!MvvmApp.getInstance().onCheckNetWork()) return;

            //   AlertDialog.Builder builder=new AlertDialog.Builder(CartActivity.this.getApplicationContext() );


            request.setAddress(locationAddress);
            request.setFlatno(house);
            request.setLocality(area);
            request.setLandmark(landmark);


            if (typeHome.get()) {
                request.setAddressTitle("Home");
                request.setAddressType(1);

            } else if (typeOffice.get()) {
                request.setAddressTitle("Office");
                request.setAddressType(2);

            } else if (typeOther.get()) {

                request.setAddressTitle(title);
                request.setAddressType(3);
            } else {
                request.setAddressTitle("UnNamed");
                request.setAddressType(3);
            }

            request.setUserid(getDataManager().getCurrentUserId());

            try {
                setIsLoading(true);
                GsonRequest gsonRequest = new GsonRequest(Request.Method.PUT, AppConstants.EAT_ADD_ADDRESS_URL, AddressResponse.class, request, new Response.Listener<AddressResponse>() {
                    @Override
                    public void onResponse(AddressResponse response) {

                        Log.e("response", response.getMessage());
                        Log.e("response", response.getMessage());
                        getNavigator().addressSaved();

                        getDataManager().updateCurrentAddress(request.getAddressTitle(), request.getAddress(), Double.parseDouble(request.getLat()), Double.parseDouble(request.getLon()), request.getLocality(), 1);



                        defaultAddress(response.getAid());



                        FilterRequestPojo filterRequestPojo;

                        Gson sGson = new GsonBuilder().create();
                        filterRequestPojo = sGson.fromJson(getDataManager().getFilterSort(), FilterRequestPojo.class);

                        filterRequestPojo.setEatuserid(getDataManager().getCurrentUserId());
                        filterRequestPojo.setLat(getDataManager().getCurrentLat());
                        filterRequestPojo.setLat(getDataManager().getCurrentLng());

                        Gson gson = new Gson();
                        String json = gson.toJson(filterRequestPojo);
                        getDataManager().setFilterSort(json);


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("fsdf",error.getMessage());
                    }
                });


                MvvmApp.getInstance().addToRequestQueue(gsonRequest);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }


        } else {

            getNavigator().emptyFields();

        }


    }


    public void fetchAddress(Integer aaid) {


        GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, AppConstants.EAT_GET_ADDRESS_URL + aaid, SingleAddressResponse.class, new Response.Listener<SingleAddressResponse>() {
            @Override
            public void onResponse(SingleAddressResponse response) {

                locationAddress.set(response.getResult().get(0).getAddress());
                area.set(response.getResult().get(0).getLocality());
                house.set(response.getResult().get(0).getFlatno());
                landmark.set(response.getResult().get(0).getLandmark());






                title.set(response.getResult().get(0).getAddressTitle());

                if (response.getResult().get(0).getAddressType() == 1) {

                    typeHome.set(true);

                }else if (response.getResult().get(0).getAddressType() == 2) {

                    typeOffice.set(true);
                }else {

                    typeOther.set(true);

                }

                getNavigator().setLatLng(response.getResult().get(0).getLat(),response.getResult().get(0).getLon());

                request.setLat(String.valueOf(response.getResult().get(0).getLat()));
                request.setLon(String.valueOf(response.getResult().get(0).getLon()));
                request.setPincode(response.getResult().get(0).getPincode());
                request.setAid( response.getResult().get(0).getAid());


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });


        MvvmApp.getInstance().addToRequestQueue(gsonRequest);
    }
    public void defaultAddress(Integer aid ){

        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.PUT, AppConstants.EAT_DEFAULT_ADDRESS, CommonResponse.class,new DefaultAddressRequest(getDataManager().getCurrentUserId(),aid), new Response.Listener<CommonResponse>() {
                @Override
                public void onResponse(CommonResponse response) {



                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                }
            });


            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }



    }



}
